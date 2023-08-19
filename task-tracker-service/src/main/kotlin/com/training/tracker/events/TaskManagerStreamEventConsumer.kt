package com.training.tracker.events

import com.training.scheme.registry.account.v1.UserStreamingEvent
import com.training.tracker.data.UserRepository
import com.training.tracker.data.model.User
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

private const val TASK_USER_STREAMING_TOPIC_NAME = "user-streaming"

@Component
class TaskManagerStreamEventConsumer(
    private val userRepository: UserRepository,
) {

    @KafkaListener(topics = [TASK_USER_STREAMING_TOPIC_NAME], groupId = "group_id")
    fun userStreamingEvent(message: ConsumerRecord<String, UserStreamingEvent>) {
        println("User streaming message delivered: $message")
        val event = message.value()

        addUser(event)
    }

    private fun addUser(event: UserStreamingEvent) {
        val user = User(
            email = event.email,
            name = event.name,
            publicId = event.publicId,
            role = event.role,
        )

        userRepository.save(user)
    }
}