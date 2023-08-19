package com.training.tracker.service

import com.training.tracker.controller.model.ReadableTaskDto
import com.training.tracker.controller.model.WritableTaskDto
import com.training.tracker.controller.model.toReadableDto
import com.training.tracker.data.TasksRepository
import com.training.tracker.data.model.toCompleteTask
import com.training.tracker.data.model.toTaskEntity
import com.training.tracker.events.TaskManagerBusinessEventProducer
import com.training.tracker.events.TaskManagerStreamEventProducer
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TaskService(
    private val tasksRepository: TasksRepository,
    private val userService: UserService,
    private val taskManagerBusinessEventProducer: TaskManagerBusinessEventProducer,
    private val taskManagerStreamEventProducer: TaskManagerStreamEventProducer,
    private val taskCostsCalculator: TaskCostsCalculator,
) {

    fun addNewTask(dto: WritableTaskDto): ReadableTaskDto {
        println("Add new task $dto")

        val user = userService.getRandomUser()
        val (jiraId, title) = extractTicketIdFromTitle(dto.title)

        val task = tasksRepository.save(
            toTaskEntity(
                assigneePublicId = user.publicId,
                assignCost = taskCostsCalculator.computeAssignCost(),
                reward = taskCostsCalculator.computeReward(),
                jiraId = jiraId,
                title = title,
            )
        )

        taskManagerBusinessEventProducer.sendTaskAdded(task)
        taskManagerStreamEventProducer.sendTaskCreated(task)

        return task.toReadableDto()
    }

    fun completeTask(taskId: Long) {
        val task = tasksRepository.findByIdOrNull(taskId) ?: throw Exception()
        val completedTask = task.toCompleteTask()

        tasksRepository.save(completedTask)
        taskManagerBusinessEventProducer.sendTaskCompleted(completedTask)
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