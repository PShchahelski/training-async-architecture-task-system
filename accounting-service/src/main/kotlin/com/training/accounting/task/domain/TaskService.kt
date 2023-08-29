package com.training.accounting.task.domain

import com.training.accounting.task.data.TaskRepository
import com.training.accounting.task.data.model.Task
import com.training.accounting.task.domain.model.DomainTask
import com.training.accounting.task.domain.model.toTask
import com.training.accounting.user.domain.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class TaskService(
    private val userService: UserService,
    private val taskRepository: TaskRepository,
) {
    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun addTask(domainTask: DomainTask): Task {
        val dbTask = findTaskByPublicId(domainTask.publicId)
        println("PASH# addTask# ${Thread.currentThread().name} task: $dbTask")

        val user = userService.findUserByPublicId(domainTask.userPublicId)

        val save = taskRepository.save(
            domainTask.toTask(
                user = user,
                id = dbTask?.id,
            )
        )
        println("PASH# addTask# ${Thread.currentThread().name} saved task: $save")

        return save
    }

    fun findTaskByPublicId(taskPublicId: String): Task? {
        return taskRepository.findByPublicId(taskPublicId)
    }
}