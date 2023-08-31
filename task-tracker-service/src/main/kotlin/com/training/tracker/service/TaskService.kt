package com.training.tracker.service

import com.github.michaelbull.result.*
import com.training.tracker.controller.model.ReadableTaskDto
import com.training.tracker.controller.model.WritableTaskDto
import com.training.tracker.controller.model.toReadableDto
import com.training.tracker.data.TasksRepository
import com.training.tracker.data.model.Task
import com.training.tracker.data.model.User
import com.training.tracker.data.model.toTaskEntity
import com.training.tracker.events.TaskBusinessEventProducer
import com.training.tracker.events.TaskStreamingEventProducer
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TaskService(
	private val tasksRepository: TasksRepository,
	private val userService: UserService,
	private val taskBusinessEventProducer: TaskBusinessEventProducer,
	private val taskStreamingEventProducer: TaskStreamingEventProducer,
	private val taskCostsCalculator: TaskCostsCalculator,
) {

	@Transactional
	fun addNewTask(dto: WritableTaskDto): ReadableTaskDto {
		println("Add new task $dto")

		val user = userService.getRandomUser()
		val (jiraId, title) = extractTicketIdFromTitle(dto.title)
		val entity = toTaskEntity(
			assigneePublicId = user.publicId,
			assignCost = taskCostsCalculator.computeAssignCost(),
			reward = taskCostsCalculator.computeReward(),
			jiraId = jiraId,
			title = title,
			user = user,
		)
		val task = tasksRepository.save(entity)
			.also(taskBusinessEventProducer::sendTaskAdded)
			.also(taskStreamingEventProducer::sendTaskCreated)


		return task.toReadableDto()
	}

	@Transactional
	fun completeTask(taskId: Long, user: User): Result<Task, Exception> {
		return tasksRepository.findByIdOrNull(taskId)
			.toResultOr { Exception("Could not find task") }
			.flatMap { task ->
				if (task.assigneePublicId != user.publicId) {
					Err(Exception("Could not complete not own task!"))
				} else {
					Ok(task)
				}
			}
			.flatMap { task ->
				task.status = Task.Status.COMPLETED
				Ok(
					tasksRepository.save(task)
						.also(taskBusinessEventProducer::sendTaskCompleted)
				)
			}
	}

	private fun extractTicketIdFromTitle(title: String): Pair<String?, String> {
		var jiraId: String? = null
		var extractedTitle = title

		if (title.contains("[") || title.contains("]")) {
			jiraId = title.substring(1, title.lastIndexOf("]"))
			extractedTitle = title.substring(jiraId.length + 5, title.length).trim()
		}

		return jiraId to extractedTitle
	}
}