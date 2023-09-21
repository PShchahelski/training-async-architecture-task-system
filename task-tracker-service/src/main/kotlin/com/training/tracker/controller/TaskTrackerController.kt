package com.training.tracker.controller

import com.github.michaelbull.result.mapBoth
import com.training.tracker.controller.model.ReadableTaskDto
import com.training.tracker.controller.model.WritableTaskDto
import com.training.tracker.data.model.User
import com.training.tracker.service.TaskService
import com.training.tracker.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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

	@PatchMapping("/{taskId}/complete")
	fun completeTask(@PathVariable(name = "taskId") taskId: Long): ResponseEntity<String> {
		val user = findUserFromAuthorization()

		return if (user == null) {
			ResponseEntity("User was not found", HttpStatus.NOT_FOUND)
		} else {
			taskService.completeTask(taskId, user)
				.mapBoth(
					success = { task -> ResponseEntity.ok(task.id.toString()) },
					failure = { exception -> ResponseEntity(exception.message, HttpStatus.BAD_REQUEST) }
				)
		}

	}

	private fun findUserFromAuthorization(): User? {
		val userEmail = extractUserEmailFromAuthorization()

		return userService.findUserByEmail(userEmail)
	}

	private fun extractUserEmailFromAuthorization(): String {
		val authentication = SecurityContextHolder.getContext().authentication

		return authentication.principal as String
	}
}