package com.training.tracker.events

import com.training.tracker.data.model.User
import com.training.tracker.data.model.toUserCreatedEventDto
import com.training.tracker.events.model.UserCreatedEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class UserEventProducer {
    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, UserCreatedEvent>

    fun sendUserCreated(user: User) {
        val event = user.toUserCreatedEventDto()
        println("Event was sent to Kafka $event")

        kafkaTemplate.send("USERS_STREAM", event.publicId, event)
    }
}