package com.training.accounting.transaction.domain

import com.training.accounting.billingcycle.domain.BillingCycleService
import com.training.accounting.events.TransactionBusinessEventProducer
import com.training.accounting.transaction.data.model.Transaction
import com.training.accounting.user.data.model.copy
import com.training.accounting.user.domain.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class TransferService(
    private val userService: UserService,
    private val billingCycleService: BillingCycleService,
    private val transactionService: TransactionService,
    private val transactionBusinessEventProducer: TransactionBusinessEventProducer,
) {
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    fun performWithdraw(
        userPublicId: String,
        taskPublicId: String,
        amount: Int,
    ) {
        val user = userService.findUserByPublicId(userPublicId)
        val billingCycle = billingCycleService.active
        val transaction = transactionService.performWithdraw(userPublicId, taskPublicId, amount, billingCycle!!.id)

        user.copy(balance = user.balance + transaction.credit)

        sendTransactionCompletedEvent(transaction)
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    fun performEnrollment(
        userPublicId: String,
        taskPublicId: String,
        amount: Int,
    ) {
        val user = userService.findUserByPublicId(userPublicId)
        val billingCycle = billingCycleService.active
        val transaction = transactionService.performEnrollment(userPublicId, taskPublicId, amount, billingCycle!!.id)

        user.copy(balance = user.balance - transaction.debit)

        sendTransactionCompletedEvent(transaction)
    }

    private fun sendTransactionCompletedEvent(transaction: Transaction) {
        transactionBusinessEventProducer.sendTransactionCompleted(transaction)
    }
}