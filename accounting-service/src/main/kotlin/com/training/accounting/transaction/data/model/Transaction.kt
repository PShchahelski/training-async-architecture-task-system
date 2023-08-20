package com.training.accounting.transaction.data.model

import com.training.scheme.registry.business.transaction.v1.TransactionCompletedBusinessEvent
import com.training.scheme.registry.business.transaction.v1.TransactionCompletedPayload
import com.training.scheme.registry.eventmeta.v1.EventMeta
import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "transactions")
class Transaction(
    val publicId: UUID = UUID.randomUUID(),
    @Enumerated(EnumType.STRING)
    val type: Type,
    val userPublicId: String,
    val debit: Int, // начисление
    val credit: Int, // списание
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    val taskPublicId: String,
    val billingCycleId: Long,
    @Id
    @GeneratedValue
    val id: Long = -1
) {

    enum class Type {
        Enrollment, //зачисление
        Withdrawal, // отчисление
        Payment,
    }
}

fun Transaction.toTransactionCompletedBusinessEvent(): TransactionCompletedBusinessEvent =
    TransactionCompletedBusinessEvent.newBuilder()
        .setPayload(
            TransactionCompletedPayload.newBuilder()
                .setCredit(credit)
                .setDebit(debit)
                .setType(type.name)
                .setUserPublicId(userPublicId)
                .setPublicId(publicId.toString())
                .setCreatedAt(createdAt.toEpochSecond())
                .build()
        )
        .setEventMeta(buildEventMeta("Transaction.Completed"))
        .build()

private fun buildEventMeta(eventName: String): EventMeta = EventMeta.newBuilder()
    .setEventType(eventName)
    .setEventId(UUID.randomUUID().toString())
    .build()