package com.training.tracker.data.model

import com.training.tracker.controller.model.WritableTaskDto
import com.training.tracker.events.model.TaskCreatedEvent
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.util.*

@Entity
class Task(
        val title: String,
        val description: String,
        val status: String,
        val userPublicId: String,
        @Column(name = "public_id", updatable = false, nullable = false)
        val publicId: UUID = UUID.randomUUID(),
        @Id
        @GeneratedValue
        val id: Long = -1
) {
    enum class Status {
        CREATED, COMPLETED
    }
}

fun WritableTaskDto.toTaskEntity(userPublicId: String) = Task(
        title = this.title,
        description = this.description,
        status = Task.Status.CREATED.toString(),
        userPublicId = userPublicId,
)

fun Task.toTaskEvent(eventName: String) = TaskCreatedEvent(
        eventName,
        userPublicId,
        publicId.toString(),
)
