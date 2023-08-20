package com.training.accounting.task.data

import com.training.accounting.task.data.model.Task
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : JpaRepository<Task, Long> {

    fun findByPublicId(publicId: String): Task?
}