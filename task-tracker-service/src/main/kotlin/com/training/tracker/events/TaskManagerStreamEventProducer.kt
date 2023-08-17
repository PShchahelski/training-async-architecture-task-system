package com.training.tracker.events

import com.training.tracker.data.model.Task
import com.training.tracker.data.model.toTaskAddedBusinessEvent
import com.training.tracker.events.model.TaskBusinessEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

private const val TASK_TOPIC_STREAMING_NAME = "task-streaming"

@Component
class TaskManagerStreamEventProducer(
        private val kafkaTemplate: KafkaTemplate<String, TaskBusinessEvent.TaskAddedBusinessEvent>
) {
    fun sendTaskCreated(task: Task) {
        val event = task.toTaskAddedBusinessEvent("Task.Created")

        println("Task Created Event: $event")
        kafkaTemplate.send(TASK_TOPIC_STREAMING_NAME, event)
    }
}