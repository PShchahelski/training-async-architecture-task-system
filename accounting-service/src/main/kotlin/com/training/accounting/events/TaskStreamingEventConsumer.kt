package com.training.accounting.events

import com.training.accounting.task.domain.TaskService
import com.training.accounting.task.domain.model.toTask
import com.training.scheme.registry.streaming.task.v1.TaskStreamingEvent
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import com.training.scheme.registry.streaming.task.v2.TaskStreamingEvent as TaskStreamingEventV2

private const val TASK_TOPIC_STREAMING_NAME = "task_management.task_streaming"

@Component
class TaskStreamingEventConsumer(
	private val taskService: TaskService,
) {

	@KafkaListener(topics = [TASK_TOPIC_STREAMING_NAME], groupId = "group_id_2")
	fun taskStreamingEvent(message: ConsumerRecord<String, SpecificRecord>) {
		println("Task streaming message delivered: $message")

		val event = message.value()
		handleReceivedMessage(event)
	}

	private fun handleReceivedMessage(event: SpecificRecord?) {
		if (event is TaskStreamingEvent && event.eventTypeVersion == 1) {
			taskService.addTask(event.payload.toTask())
		} else if (event is TaskStreamingEventV2 && event.eventTypeVersion == 2) {
			taskService.addTask(event.payload.toTask())
		} else {
			// send alert to monitoring system
		}
	}
}