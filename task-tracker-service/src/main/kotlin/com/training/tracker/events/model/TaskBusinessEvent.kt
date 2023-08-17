package com.training.tracker.events.model


sealed interface TaskBusinessEvent {

    data class TaskAddedBusinessEvent(
            val eventName: String,
            val assigneePublicId: String,
            val assignCost: Int,
            val reward: Int,
            val publicId: String,
    ) : TaskBusinessEvent

    data class TaskCompletedBusinessEvent(
            val eventName: String,
            val assigneePublicId: String,
            val assignCost: Int,
            val reward: Int,
            val publicId: String,
    ) : TaskBusinessEvent
}