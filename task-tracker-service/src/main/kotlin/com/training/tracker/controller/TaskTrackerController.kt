package com.training.tracker.controller

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapBoth
import com.training.tracker.controller.model.ReadableTaskDto
import com.training.tracker.controller.model.WritableTaskDto
import com.training.tracker.data.model.User
import com.training.tracker.service.TaskService
import com.training.tracker.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
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
		return findUserFromAuthorization()
			.mapBoth(
				success = { user ->
					taskService.completeTask(taskId, user)
						.mapBoth(
							success = { task -> ResponseEntity.ok(task.id.toString()) },
							failure = { exception -> ResponseEntity(exception.message, HttpStatus.BAD_REQUEST) }
						)
				},
				failure = { exception -> ResponseEntity(exception.message, HttpStatus.NOT_FOUND) }
			)
	}

	private fun findUserFromAuthorization(): Result<User, UsernameNotFoundException> {
		val userEmail = extractUserEmailFromAuthorization()

		return userService.findUserByEmail(userEmail)
	}

	private fun extractUserEmailFromAuthorization(): String {
		val authentication = SecurityContextHolder.getContext().authentication

		return authentication.principal as String
	}
}