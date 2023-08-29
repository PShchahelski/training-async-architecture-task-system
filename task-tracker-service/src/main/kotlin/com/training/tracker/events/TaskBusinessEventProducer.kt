package com.training.tracker.events

import com.training.tracker.data.model.Task
import com.training.tracker.data.model.toCompleteTaskBusinessEvent
import com.training.tracker.data.model.toTaskAddedBusinessEventV2
import org.apache.avro.specific.SpecificRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

private const val TASK_TOPIC_NAME = "task_management.task_lifecycle"

@Component
class TaskBusinessEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, SpecificRecord>
) {

    fun sendTaskAdded(task: Task) {
        val event = task.toTaskAddedBusinessEventV2()

        println("Task added event sent: $event")
        kafkaTemplate.send(TASK_TOPIC_NAME, event)
    }

    fun sendTaskCompleted(task: Task) {
        val event = task.toCompleteTaskBusinessEvent()

        println("Task completed event sent: $event")
        kafkaTemplate.send(TASK_TOPIC_NAME, event)
    }
}
