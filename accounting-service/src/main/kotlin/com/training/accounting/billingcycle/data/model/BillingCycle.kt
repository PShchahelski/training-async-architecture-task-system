package com.training.accounting.billingcycle.data.model

import com.training.accounting.transaction.data.model.Transaction
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "billing_cycles")
class BillingCycle(
    @Id
    @GeneratedValue
    val id: Long = -1,
    @Enumerated(EnumType.STRING)
    val status: Status,
    val startDatetime: OffsetDateTime,
    val endDatetime: OffsetDateTime? = null,
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "billingCycle")
    val transactions: List<Transaction> = emptyList(),
) {
    enum class Status {
        Active,
        Closed,
    }
}