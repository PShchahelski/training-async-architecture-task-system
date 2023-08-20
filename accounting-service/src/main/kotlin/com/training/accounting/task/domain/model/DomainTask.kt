package com.training.accounting.task.domain.model

import com.training.accounting.task.data.model.Task
import com.training.scheme.registry.business.task.v1.TaskAddedPayload
import com.training.scheme.registry.streaming.task.v1.TaskStreamingPayload
import com.training.scheme.registry.business.task.v2.TaskAddedPayload as TaskAddedPayloadV2
import com.training.scheme.registry.streaming.task.v2.TaskStreamingPayload as TaskStreamingPayloadV2

data class DomainTask(
    val title: String,
    val jiraId: String? = null,
    val userPublicId: String,
    val publicId: String,
    val assignCost: Int,
    val reward: Int,
)

fun DomainTask.toTask(id: Long?) = Task(
    id = id ?: -1,
    title = title,
    jiraId = jiraId,
    userPublicId = userPublicId,
    publicId = publicId,
    assignCost = assignCost,
    reward = reward,
)

fun TaskStreamingPayload.toTask() = DomainTask(
    title = title,
    userPublicId = assigneePublicId,
    publicId = publicId,
    assignCost = assignCost,
    reward = reward,
)

fun TaskStreamingPayloadV2.toTask() = DomainTask(
    title = title,
    jiraId = jiraId,
    userPublicId = assigneePublicId,
    publicId = publicId,
    assignCost = assignCost,
    reward = reward,
)

fun TaskAddedPayload.toTask() = DomainTask(
    title = title,
    userPublicId = assigneePublicId,
    publicId = publicId,
    assignCost = assignCost,
    reward = reward,
)

fun TaskAddedPayloadV2.toTask() = DomainTask(
    title = title,
    jiraId = jiraId,
    userPublicId = assigneePublicId,
    publicId = publicId,
    assignCost = assignCost,
    reward = reward,
)