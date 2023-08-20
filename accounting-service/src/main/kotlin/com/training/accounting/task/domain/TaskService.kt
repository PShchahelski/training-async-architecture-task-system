package com.training.accounting.task.domain

import com.training.accounting.task.data.TaskRepository
import com.training.accounting.task.domain.model.DomainTask
import com.training.accounting.task.domain.model.toTask
import org.springframework.stereotype.Service

@Service
class TaskService(
    private val taskRepository: TaskRepository,
) {
    fun addTask(domainTask: DomainTask) {
        val dbTaskId = taskRepository.findByPublicId(domainTask.publicId)?.id

        taskRepository.save(domainTask.toTask(dbTaskId))
    }
}