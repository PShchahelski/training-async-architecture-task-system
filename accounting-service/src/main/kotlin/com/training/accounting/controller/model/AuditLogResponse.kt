package com.training.accounting.controller.model

import com.training.accounting.transaction.data.model.Transaction
import java.time.OffsetDateTime

data class AuditLogResponse(
    val transactions: List<TransactionDto>,
)

data class TransactionDto(
    val type: Transaction.Type,
    val billingCycleId: Long,
    val amount: Int,
    val debit: Int,
    val credit: Int,
    val createdAt: OffsetDateTime,
)