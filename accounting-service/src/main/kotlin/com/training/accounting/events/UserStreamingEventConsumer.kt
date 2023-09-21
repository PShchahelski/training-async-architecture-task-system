package com.training.accounting.events

import com.training.accounting.billingcycle.domain.BillingCycleService
import com.training.accounting.user.domain.UserService
import com.training.scheme.registry.streaming.account.v1.UserStreamingEvent
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

private const val USER_STREAMING_TOPIC_NAME = "auth.user_streaming"

@Component
class UserStreamingEventConsumer(
    private val userService: UserService,
    private val billingCycleService: BillingCycleService,
) {

    @KafkaListener(topics = [USER_STREAMING_TOPIC_NAME], groupId = "accounting.user_streaming_group")
    fun userStreamingEvent(message: ConsumerRecord<String, UserStreamingEvent>) {
        println("User streaming message delivered: $message")
        val event = message.value()

        val user = userService.addUser(event.payload)
        billingCycleService.openBillingCycle(user)
    }
}