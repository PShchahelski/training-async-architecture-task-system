package com.training.tracker.events

import com.training.scheme.registry.task.v1.TaskStreamingEvent
import com.training.tracker.data.model.Task
import com.training.tracker.data.model.toTaskStreamEventDto
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

private const val TASK_TOPIC_STREAMING_NAME = "task-streaming"

@Component
class TaskManagerStreamEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, TaskStreamingEvent>
) {
    fun sendTaskCreated(task: Task) {
        val event = task.toTaskStreamEventDto("Task.Created")

        println("Task Created Event: $event")
        kafkaTemplate.send(TASK_TOPIC_STREAMING_NAME, event)
    }
}