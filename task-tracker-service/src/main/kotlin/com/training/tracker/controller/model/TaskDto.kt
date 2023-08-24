package com.training.tracker.controller.model

import com.training.tracker.data.model.Task

data class WritableTaskDto(
    val title: String,
)

data class ReadableTaskDto(
    val taskId: Long,
    val publicId: String,
    val title: String,
    val status: String,
    val userPublicId: String,
)

fun Task.toReadableDto() = ReadableTaskDto(
    id,
    publicId.toString(),
    title,
    status.name,
    assigneePublicId,
)
