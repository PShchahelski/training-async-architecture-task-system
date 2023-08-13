package com.training.tracker.service

import com.training.tracker.controller.model.ReadableTaskDto
import com.training.tracker.controller.model.WritableTaskDto
import com.training.tracker.controller.model.toReadableDto
import com.training.tracker.data.TasksRepository
import com.training.tracker.data.model.toTaskEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TaskService {

    @Autowired
    private lateinit var tasksRepository: TasksRepository

    @Autowired
    private lateinit var userService: UserService

    fun addNewTask(taskDto: WritableTaskDto): ReadableTaskDto {
        println("Add new task $taskDto")

        val user = userService.getRandomUser()
        val task = tasksRepository.save(taskDto.toTaskEntity(user.publicId))

        return task.toReadableDto()
    }
}