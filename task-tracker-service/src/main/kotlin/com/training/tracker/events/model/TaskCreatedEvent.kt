package com.training.tracker.events.model

data class TaskCreatedEvent(
        val eventName: String,
        val userPublicId: String,
        val publicId: String,
)