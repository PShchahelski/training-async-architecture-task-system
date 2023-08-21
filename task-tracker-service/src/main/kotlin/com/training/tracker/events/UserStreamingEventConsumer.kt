package com.training.tracker.events

import com.training.scheme.registry.streaming.account.v1.UserStreamingEvent
import com.training.scheme.registry.streaming.account.v1.UserStreamingPayload
import com.training.tracker.data.UserRepository
import com.training.tracker.data.model.User
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

private const val USER_STREAMING_TOPIC_NAME = "user-streaming"

@Component
class UserStreamingEventConsumer(
    private val userRepository: UserRepository,
) {

    @KafkaListener(topics = [USER_STREAMING_TOPIC_NAME], groupId = "group_id")
    fun userStreamingEvent(message: ConsumerRecord<String, UserStreamingEvent>) {
        println("User streaming message delivered: $message")
        val event = message.value()

        addUser(event.payload)
    }

    private fun addUser(payload: UserStreamingPayload) {
        val user = User(
            email = payload.email,
            name = payload.name,
            publicId = payload.publicId,
            role = payload.role,
        )

        userRepository.save(user)
    }
}