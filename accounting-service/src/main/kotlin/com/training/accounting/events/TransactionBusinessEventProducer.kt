package com.training.accounting.events

import com.training.accounting.transaction.data.model.Transaction
import com.training.accounting.transaction.data.model.toPaymentTransaction
import com.training.accounting.transaction.data.model.toTransactionCompletedBusinessEvent
import org.apache.avro.specific.SpecificRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component


private const val TASK_TOPIC_NAME = "transaction-lifecycle"

@Component
class TransactionBusinessEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, SpecificRecord>
) {

    fun sendTransactionCompleted(transaction: Transaction) {
        val event = transaction.toTransactionCompletedBusinessEvent()

        kafkaTemplate.send(TASK_TOPIC_NAME, event)
    }

    fun sendPaymentTransaction(transaction: Transaction) {
        val event = transaction.toPaymentTransaction(transaction.debit)

        kafkaTemplate.send(TASK_TOPIC_NAME, event)
    }
}