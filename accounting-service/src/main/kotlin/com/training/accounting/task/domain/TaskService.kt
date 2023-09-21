package com.training.accounting.task.domain

import com.training.accounting.task.data.TaskRepository
import com.training.accounting.task.data.model.Task
import com.training.accounting.task.domain.model.DomainTask
import com.training.accounting.task.domain.model.toTask
import com.training.accounting.user.domain.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TaskService(
	private val userService: UserService,
	private val taskRepository: TaskRepository,
) {
	@Transactional
	fun addTask(domainTask: DomainTask): Task? {
		val dbTask = findTaskByPublicId(domainTask.publicId)

		return userService.findUserByPublicId(domainTask.userPublicId)
			?.let { user ->
				taskRepository.save(
					domainTask.toTask(
						user = user,
						id = dbTask?.id,
					)
				)
			}
	}

	fun findTaskByPublicId(taskPublicId: String): Task? {
		return taskRepository.findByPublicId(taskPublicId)
	}
}