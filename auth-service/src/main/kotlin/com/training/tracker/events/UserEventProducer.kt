package com.training.tracker.events

import com.training.scheme.registry.account.v1.UserStreamingEvent
import com.training.tracker.data.model.User
import com.training.tracker.data.model.toUserStreamingEventDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

private const val USER_STREAMING_TOPIC_NAME = "user-streaming"

@Component
class UserEventProducer {
    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, UserStreamingEvent>

    fun sendUserCreated(user: User) {
        val event = user.toUserStreamingEventDto("User.Created")
        println("User streaming event was sent to broker $event")

        kafkaTemplate.send(USER_STREAMING_TOPIC_NAME, event.publicId, event)
    }
}