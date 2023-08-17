package com.training.tracker.events.model

sealed interface UserStreamingEvent {

    data class UserCreatedStreamingEvent(
            val email: String,
            val name: String,
            val role: String,
            val publicId: String,
            val eventName: String
    ) : UserStreamingEvent
}