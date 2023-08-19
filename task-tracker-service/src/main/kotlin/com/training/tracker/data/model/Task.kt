package com.training.tracker.data.model

import com.training.scheme.registry.task.v1.TaskAddedBusinessEvent
import com.training.scheme.registry.task.v1.TaskCompletedBusinessEvent
import com.training.scheme.registry.task.v1.TaskStreamingEvent
import com.training.tracker.controller.model.WritableTaskDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.util.*

@Entity
class Task(
    val title: String,
    val jiraId: String? = null,
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
    jiraId: String?,
    title: String,
) = Task(
    title = title,
    status = Task.Status.CREATED.toString(),
    assigneePublicId = assigneePublicId,
    assignCost = assignCost,
    reward = reward,
    jiraId = jiraId,
)

fun Task.toCompleteTask() = Task(
    title = this.title,
    status = Task.Status.COMPLETED.toString(),
    assigneePublicId = assigneePublicId,
    assignCost = assignCost,
    reward = reward,
)

fun Task.toCompleteTaskBusinessEvent(): TaskCompletedBusinessEvent = TaskCompletedBusinessEvent.newBuilder()
    .setEventId(UUID.randomUUID().toString())
    .setPublicId(publicId.toString())
    .build()

fun Task.toTaskAddedBusinessEvent(): TaskAddedBusinessEvent = TaskAddedBusinessEvent.newBuilder()
    .setEventId(UUID.randomUUID().toString())
    .setAssigneePublicId(assigneePublicId)
    .setPublicId(publicId.toString())
    .build()

fun Task.toTaskStreamEventDto(eventName: String): TaskStreamingEvent {
    return TaskStreamingEvent.newBuilder()
        .setPublicId(publicId.toString())
        .setTitle(title)
        .setAssignCost(assignCost)
        .setReward(reward)
        .setAssigneePublicId(assigneePublicId)
        .setEventId(UUID.randomUUID().toString())
        .setEventName(eventName)
        .build()
}
