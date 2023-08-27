package com.training.tracker.events

import com.training.tracker.data.model.Task
import com.training.tracker.data.model.toTaskStreamEventDtoV2
import org.apache.avro.specific.SpecificRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

private const val TASK_TOPIC_STREAMING_NAME = "task_management.task_streaming"

@Component
class TaskStreamingEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, SpecificRecord>
) {
    fun sendTaskCreated(task: Task) {
        val event = task.toTaskStreamEventDtoV2("Task.Created")

        println("Task Created Event: $event")
        kafkaTemplate.send(TASK_TOPIC_STREAMING_NAME, event)
    }
}