package com.training.tracker.events.model

data class UserCreatedEvent(
        val email: String,
        val name: String,
        val role: String,
        val publicId: String,
        val eventName: String = "UserCreated"
)
