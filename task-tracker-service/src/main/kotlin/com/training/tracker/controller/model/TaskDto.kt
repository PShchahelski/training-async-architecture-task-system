package com.training.tracker.controller.model

import com.training.tracker.data.model.Task

data class WritableTaskDto(
        val title: String,
        val description: String,
)

data class ReadableTaskDto(
        val taskId: Long,
        val publicId: String,
        val title: String,
        val description: String,
        val status: String,
        val userPublicId: String,
)

fun Task.toReadableDto() = ReadableTaskDto(
        this.id,
        this.publicId.toString(),
        this.title,
        this.description,
        this.status,
        this.userPublicId,
)

fun Task.toWritableDto() = WritableTaskDto(
        this.title,
        this.description,
)