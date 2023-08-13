package com.training.tracker.controller

import com.training.tracker.controller.model.ReadableTaskDto
import com.training.tracker.controller.model.WritableTaskDto
import com.training.tracker.service.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tasks")
class TaskTrackerController {

    @Autowired
    private lateinit var taskService: TaskService

    @PostMapping
    fun addNewTask(@RequestBody task: WritableTaskDto): ReadableTaskDto {
        return taskService.addNewTask(task)
    }
}