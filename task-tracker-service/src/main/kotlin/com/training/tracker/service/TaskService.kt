package com.training.tracker.service

import com.training.tracker.controller.model.ReadableTaskDto
import com.training.tracker.controller.model.WritableTaskDto
import com.training.tracker.controller.model.toReadableDto
import com.training.tracker.data.TasksRepository
import com.training.tracker.data.model.toCompletedTask
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
        val task = tasksRepository.save(dto.toTaskEntity(
                assigneePublicId = user.publicId,
                assignCost = taskCostsCalculator.computeAssignCost(),
                reward = taskCostsCalculator.computeReward(),
        ))

        taskManagerBusinessEventProducer.sendTaskAdded(task)
        taskManagerStreamEventProducer.sendTaskCreated(task)

        return task.toReadableDto()
    }

    fun completeTask(taskId: Long) {
        val task = tasksRepository.findByIdOrNull(taskId) ?: throw Exception()
        val completedTask = task.toCompletedTask()

        tasksRepository.save(completedTask)
        taskManagerBusinessEventProducer.sendTaskCompleted(task)
    }
}