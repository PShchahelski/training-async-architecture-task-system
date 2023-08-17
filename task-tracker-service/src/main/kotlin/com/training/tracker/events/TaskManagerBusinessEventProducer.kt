package com.training.tracker.events

import com.training.tracker.data.model.Task
import com.training.tracker.data.model.toTaskAddedBusinessEvent
import com.training.tracker.events.model.TaskBusinessEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

private const val TASK_TOPIC_NAME = "task-lifecycle"

@Component
class TaskManagerBusinessEventProducer(
        private val kafkaTemplate: KafkaTemplate<String, TaskBusinessEvent>
) {

    fun sendTaskAdded(task: Task) {
        val event = task.toTaskAddedBusinessEvent("Task.Added")

        println("Task Created event sent: $event")
        kafkaTemplate.send(TASK_TOPIC_NAME, event)
    }

    fun sendTaskCompleted(task: Task) {
        val event = task.toTaskAddedBusinessEvent("Task.Completed")

        println("Task Created event sent: $event")
        kafkaTemplate.send(TASK_TOPIC_NAME, event)
    }
}
