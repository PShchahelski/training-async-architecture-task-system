package com.training.tracker.controller

import com.training.tracker.controller.model.ReadableTaskDto
import com.training.tracker.controller.model.WritableTaskDto
import com.training.tracker.data.model.User
import com.training.tracker.service.TaskService
import com.training.tracker.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tasks")
class TaskTrackerController(
	private val taskService: TaskService,
	private val userService: UserService,
) {

	@PostMapping
	fun addNewTask(@RequestBody dto: WritableTaskDto): ReadableTaskDto {
		return taskService.addNewTask(dto)
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PatchMapping("/{taskId}/complete")
	fun completeTask(@PathVariable(name = "taskId") taskId: Long) {
		val user = findUserFromAuthorization()

		taskService.completeTask(taskId, user)
	}


	private fun findUserFromAuthorization(): User {
		val userEmail = extractUserEmailFromAuthorization()

		return userService.findUserByEmail(userEmail)
	}

	private fun extractUserEmailFromAuthorization(): String {
		val authentication = SecurityContextHolder.getContext().authentication

		return authentication.principal as String
	}
}