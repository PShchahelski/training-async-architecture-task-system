package com.training.accounting.billingcycle.data.model

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
) {
    enum class Status {
        Active,
        Closed,
    }
}