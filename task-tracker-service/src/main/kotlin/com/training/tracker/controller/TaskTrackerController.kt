package com.training.tracker.controller

import com.training.tracker.controller.model.TaskDto
import com.training.tracker.service.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/tasks")
class TaskTrackerController {

    @Autowired
    private lateinit var taskService: TaskService

    @GetMapping
    fun getAllTasksByAccount(): List<TaskDto> {
        return taskService.getTasksByAccountId()
    }

    @PostMapping
    fun addNewTask(@RequestBody task: TaskDto): TaskDto {
        return taskService.addNewTask(task)
    }
}