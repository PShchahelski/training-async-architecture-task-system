package com.training.accounting.transaction.data.model

import com.training.accounting.billingcycle.data.model.BillingCycle
import com.training.accounting.task.data.model.Task
import com.training.accounting.user.data.model.User
import com.training.scheme.registry.business.transaction.v1.TransactionCompletedBusinessEvent
import com.training.scheme.registry.business.transaction.v1.TransactionCompletedPayload
import com.training.scheme.registry.business.transaction.v1.TransactionPaymentBusinessEvent
import com.training.scheme.registry.business.transaction.v1.TransactionPaymentPayload
import com.training.scheme.registry.eventmeta.v1.EventMeta
import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "transactions")
class Transaction(
    @Column(name = "public_id", updatable = false, nullable = false)
    val publicId: UUID = UUID.randomUUID(),
    @Enumerated(EnumType.STRING)
    val type: Type,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    val user: User,
    val debit: Int, // снятие
    val credit: Int, // начисление
    @Column(name = "created_at", updatable = false, nullable = false)
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    val task: Task? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_cycle_id")
    val billingCycle: BillingCycle? = null,
    @Id
    @GeneratedValue
    val id: Long = -1,
) {

	enum class Type {
		Deposit, // отчисление
		Withdrawal, // зачисление
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
				.setUserPublicId(user.publicId)
				.setPublicId(publicId.toString())
				.setCreatedAt(createdAt.toInstant())
				.build()
		)
		.setEventMeta(buildEventMeta("Transaction.Completed"))
		.build()

fun Transaction.toPaymentTransaction(value: Int): TransactionPaymentBusinessEvent =
	TransactionPaymentBusinessEvent.newBuilder()
		.setPayload(
			TransactionPaymentPayload.newBuilder()
				.setUserPublicId(user.publicId)
				.setPublicId(publicId.toString())
				.setValue(value)
				.setCreatedAt(createdAt.toInstant())
				.build()
		)
		.setEventMeta(buildEventMeta("Transaction.Completed"))
		.build()

private fun buildEventMeta(eventName: String): EventMeta = EventMeta.newBuilder()
	.setEventType(eventName)
	.setEventId(UUID.randomUUID().toString())
	.build()