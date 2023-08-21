package com.training.tracker.controller

import com.training.tracker.controller.model.ReadableTaskDto
import com.training.tracker.controller.model.WritableTaskDto
import com.training.tracker.service.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tasks")
class TaskTrackerController {

    @Autowired
    private lateinit var taskService: TaskService

    @PostMapping
    fun addNewTask(@RequestBody dto: WritableTaskDto): ReadableTaskDto {
        return taskService.addNewTask(dto)
    }

    @PostMapping("/{id}/complete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun completeTask(@PathVariable taskId: Long) {
        taskService.completeTask(taskId)
    }
}