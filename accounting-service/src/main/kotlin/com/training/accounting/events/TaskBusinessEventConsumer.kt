package com.training.accounting.events

import com.training.accounting.task.domain.TaskService
import com.training.accounting.task.domain.model.toTask
import com.training.accounting.transaction.domain.TransferService
import com.training.scheme.registry.business.task.v1.TaskAddedBusinessEvent
import com.training.scheme.registry.business.task.v1.TaskAddedPayload
import com.training.scheme.registry.business.task.v1.TaskCompletedBusinessEvent
import com.training.scheme.registry.business.task.v1.TaskCompletedPayload
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import com.training.scheme.registry.business.task.v2.TaskAddedBusinessEvent as TaskAddedBusinessEventV2
import com.training.scheme.registry.business.task.v2.TaskAddedPayload as TaskAddedPayloadV2

private const val TASK_TOPIC_NAME = "task-lifecycle"

@Component
class TaskBusinessEventConsumer(
    private val transferService: TransferService,
    private val taskService: TaskService,
) {

    @KafkaListener(topics = [TASK_TOPIC_NAME], groupId = "group_id_2")
    fun taskBusinessEvent(message: ConsumerRecord<String, SpecificRecord>) {
        println("Task business message delivered: $message")

        when (val event = message.value()) {
            is TaskAddedBusinessEvent -> taskAdded(event.payload)
            is TaskAddedBusinessEventV2 -> taskAdded(event.payload)
            is TaskCompletedBusinessEvent -> taskCompleted(event.payload)
        }
    }

    private fun taskAdded(payload: TaskAddedPayload) {
        taskService.addTask(payload.toTask())
        transferService.performDeposit(
            userPublicId = payload.assigneePublicId,
            taskPublicId = payload.publicId,
            amount = payload.assignCost,
        )
    }

    private fun taskAdded(payload: TaskAddedPayloadV2) {
        println("PASH# taskAdded# ${Thread.currentThread().name}")
        taskService.addTask(payload.toTask())
        transferService.performDeposit(
            userPublicId = payload.assigneePublicId,
            taskPublicId = payload.publicId,
            amount = payload.assignCost,
        )
    }

    private fun taskCompleted(payload: TaskCompletedPayload) {
        transferService.performWithdraw(
            userPublicId = payload.assigneePublicId,
            taskPublicId = payload.publicId,
            amount = payload.reward,
        )
    }
}