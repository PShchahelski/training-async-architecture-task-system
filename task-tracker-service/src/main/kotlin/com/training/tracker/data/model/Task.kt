package com.training.tracker.data.model

import com.training.scheme.registry.business.task.v1.TaskAddedBusinessEvent
import com.training.scheme.registry.business.task.v1.TaskAddedPayload
import com.training.scheme.registry.business.task.v1.TaskCompletedBusinessEvent
import com.training.scheme.registry.business.task.v1.TaskCompletedPayload
import com.training.scheme.registry.eventmeta.v1.EventMeta
import com.training.scheme.registry.streaming.task.v1.TaskStreamingEvent
import com.training.scheme.registry.streaming.task.v1.TaskStreamingPayload
import jakarta.persistence.*
import java.util.*
import com.training.scheme.registry.business.task.v2.TaskAddedBusinessEvent as TaskAddedBusinessEventV2
import com.training.scheme.registry.business.task.v2.TaskAddedPayload as TaskAddedPayloadV2
import com.training.scheme.registry.streaming.task.v2.TaskStreamingEvent as TaskStreamingEventV2
import com.training.scheme.registry.streaming.task.v2.TaskStreamingPayload as TaskStreamingPayloadV2

@Entity
@Table(name = "tasks")
class Task(
    val title: String,
    val jiraId: String? = null,
    @Enumerated(EnumType.STRING)
    var status: Status,
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

fun toTaskEntity(
    assigneePublicId: String,
    assignCost: Int,
    reward: Int,
    jiraId: String?,
    title: String,
) = Task(
    title = title,
    status = Task.Status.CREATED,
    assigneePublicId = assigneePublicId,
    assignCost = assignCost,
    reward = reward,
    jiraId = jiraId,
)

fun Task.toCompleteTaskBusinessEvent(): TaskCompletedBusinessEvent = TaskCompletedBusinessEvent.newBuilder()
    .setPayload(
        TaskCompletedPayload.newBuilder()
            .setReward(reward)
            .setPublicId(publicId.toString())
            .setAssigneePublicId(assigneePublicId)
            .build()
    )
    .setEventMeta(buildEventMeta("Task.Completed"))
    .build()

fun Task.toTaskAddedBusinessEventV1(): TaskAddedBusinessEvent = TaskAddedBusinessEvent.newBuilder()
    .setPayload(
        TaskAddedPayload.newBuilder()
            .setReward(reward)
            .setAssignCost(assignCost)
            .setTitle(title)
            .setAssigneePublicId(assigneePublicId)
            .setPublicId(publicId.toString())
            .build()
    )
    .setEventMeta(buildEventMeta("Task.Added"))
    .build()

fun Task.toTaskAddedBusinessEventV2(): TaskAddedBusinessEventV2 = TaskAddedBusinessEventV2.newBuilder()
    .setPayload(
        TaskAddedPayloadV2.newBuilder()
            .setReward(reward)
            .setAssignCost(assignCost)
            .setTitle(title)
            .setJiraId(jiraId)
            .setAssigneePublicId(assigneePublicId)
            .setPublicId(publicId.toString())
            .build()
    )
    .setEventMeta(buildEventMeta("Task.Added"))
    .build()

fun Task.toTaskStreamEventDto(eventName: String): TaskStreamingEvent {
    return TaskStreamingEvent.newBuilder()
        .setPayload(
            TaskStreamingPayload.newBuilder()
                .setPublicId(publicId.toString())
                .setTitle(title)
                .setAssignCost(assignCost)
                .setReward(reward)
                .setAssigneePublicId(assigneePublicId)
                .build()
        )
        .setEventMeta(buildEventMeta(eventName))
        .build()
}

fun Task.toTaskStreamEventDtoV2(eventName: String): TaskStreamingEventV2 {
    return TaskStreamingEventV2.newBuilder()
        .setPayload(
            TaskStreamingPayloadV2.newBuilder()
                .setPublicId(publicId.toString())
                .setTitle(title)
                .setJiraId(jiraId)
                .setAssignCost(assignCost)
                .setReward(reward)
                .setAssigneePublicId(assigneePublicId)
                .build()
        )
        .setEventMeta(buildEventMeta(eventName))
        .build()
}

private fun buildEventMeta(eventName: String): EventMeta = EventMeta.newBuilder()
    .setEventType(eventName)
    .setEventId(UUID.randomUUID().toString())
    .build()
