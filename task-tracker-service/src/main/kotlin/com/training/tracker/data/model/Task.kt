package com.training.tracker.data.model

import com.training.tracker.controller.model.WritableTaskDto
import com.training.tracker.events.model.TaskBusinessEvent
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
        val assigneePublicId: String,
        @Column(name = "public_id", updatable = false, nullable = false)
        val publicId: UUID = UUID.randomUUID(),
        val assignCost: Int,
        val reward: Int,
        @Id
        @GeneratedValue
        val id: Long = -1
) {
    enum class Status {
        CREATED, COMPLETED
    }
}

fun WritableTaskDto.toTaskEntity(
        assigneePublicId: String,
        assignCost: Int,
        reward: Int,
) = Task(
        title = this.title,
        description = this.description,
        status = Task.Status.CREATED.toString(),
        assigneePublicId = assigneePublicId,
        assignCost = assignCost,
        reward = reward,
)

fun Task.toCompletedTask() = Task(
        title = this.title,
        description = this.description,
        status = Task.Status.COMPLETED.toString(),
        assigneePublicId = assigneePublicId,
        assignCost = assignCost,
        reward = reward,
)

fun Task.toTaskAddedBusinessEvent(eventName: String) = TaskBusinessEvent.TaskAddedBusinessEvent(
        eventName = eventName,
        assigneePublicId = assigneePublicId,
        reward = reward,
        assignCost = assignCost,
        publicId = publicId.toString(),
)
