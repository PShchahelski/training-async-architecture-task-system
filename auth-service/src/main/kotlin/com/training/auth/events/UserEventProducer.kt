package com.training.auth.events

import com.training.auth.data.model.User
import com.training.auth.data.model.toUserStreamingEventDto
import com.training.scheme.registry.streaming.account.v1.UserStreamingEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

private const val USER_STREAMING_TOPIC_NAME = "auth.user_streaming"

@Component
class UserEventProducer(
	private val kafkaTemplate: KafkaTemplate<String, UserStreamingEvent>,
) {
	fun sendUserCreated(user: User) {
		val event = user.toUserStreamingEventDto("User.Created")
		println("User streaming event was sent to broker $event")

		kafkaTemplate.send(USER_STREAMING_TOPIC_NAME, event.payload.publicId, event)
	}
}