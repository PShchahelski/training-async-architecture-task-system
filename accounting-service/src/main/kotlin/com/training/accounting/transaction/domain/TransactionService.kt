package com.training.accounting.transaction.domain

import com.training.accounting.transaction.data.TransactionRepository
import com.training.accounting.transaction.data.model.Transaction
import org.springframework.stereotype.Service

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
) {
    fun performWithdraw(
        userPublicId: String,
        taskPublicId: String,
        amount: Int,
        billingCycleId: Long
    ): Transaction {
        val transaction = Transaction(
            type = Transaction.Type.Withdrawal,
            userPublicId = userPublicId,
            taskPublicId = taskPublicId,
            debit = 0,
            credit = amount,
            billingCycleId = billingCycleId,
        )

        return transactionRepository.save(transaction)
    }

    fun performEnrollment(
        userPublicId: String,
        taskPublicId: String,
        amount: Int,
        billingCycleId: Long
    ): Transaction {
        val transaction = Transaction(
            type = Transaction.Type.Enrollment,
            userPublicId = userPublicId,
            taskPublicId = taskPublicId,
            debit = amount,
            credit = 0,
            billingCycleId = billingCycleId,
        )

        return transactionRepository.save(transaction)
    }

    fun findAllByUserId(userPublicId: String): List<Transaction> {
        return transactionRepository.findAllByUserPublicId(userPublicId)
    }
}