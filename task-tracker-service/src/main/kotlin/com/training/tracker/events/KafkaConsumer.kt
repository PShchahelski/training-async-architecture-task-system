package com.training.tracker.events

import com.training.tracker.data.UserRepository
import com.training.tracker.data.model.User
import com.training.tracker.events.model.UserCreatedEvent
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumer {

    @Autowired
    private lateinit var userRepository: UserRepository

    @KafkaListener(topics = ["USERS_STREAM"], groupId = "group_id")
    fun consume(message: ConsumerRecord<String, UserCreatedEvent>) {
        println("Message Delivered: $message")

        val streamEvent = message.value()
        val user = User(email = streamEvent.email,
                name = streamEvent.name,
                publicId = streamEvent.publicId,
                role = streamEvent.role,
        )

        userRepository.save(user)
    }
}