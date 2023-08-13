package com.training.tracker.events

import com.training.tracker.data.model.Task
import com.training.tracker.data.model.toTaskEvent
import com.training.tracker.events.model.TaskCreatedEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class TaskManagerEventProducer {

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, TaskCreatedEvent>

    fun sendTaskCreated(task: Task) {
        val event = task.toTaskEvent("TaskCreated")

        println("Task Created event sent: $event")
        kafkaTemplate.send("TASK_STREAM", event)

    }
}
