package com.training.accounting.billingcycle.data.model

import com.training.accounting.transaction.data.model.Transaction
import com.training.accounting.user.data.model.User
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "billing_cycles")
class BillingCycle(
    @Id
    @GeneratedValue
    val id: Long = -1,
    @Enumerated(EnumType.STRING)
    var status: Status,
    val startDatetime: OffsetDateTime,
    var endDatetime: OffsetDateTime? = null,
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "billingCycle")
    val transactions: List<Transaction> = emptyList(),
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,
) {
    enum class Status {
        Active,
        Closed,
    }
}