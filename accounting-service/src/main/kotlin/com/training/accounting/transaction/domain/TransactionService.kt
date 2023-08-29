package com.training.accounting.transaction.domain

import com.training.accounting.billingcycle.domain.BillingCycleService
import com.training.accounting.task.domain.TaskService
import com.training.accounting.transaction.data.TransactionRepository
import com.training.accounting.transaction.data.model.Transaction
import com.training.accounting.user.data.model.User
import com.training.accounting.user.domain.UserService
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class TransactionService(
    private val userService: UserService,
    private val taskService: TaskService,
    private val billingCycleService: BillingCycleService,
    private val transactionRepository: TransactionRepository,
) {
    fun addWithdrawTransaction(
        userPublicId: String,
        taskPublicId: String,
        amount: Int,
        billingCycleId: Long
    ): Transaction {
        val user = userService.findUserByPublicId(userPublicId)
        //TODO: handle exception
        val task = taskService.findTaskByPublicId(taskPublicId)!!
        val billingCycle = billingCycleService.getActiveBillingCycle(user.id)!!

        val transaction = Transaction(
            type = Transaction.Type.Withdrawal,
            debit = 0,
            credit = amount,
            task = task,
            user = user,
            billingCycle = billingCycle,
        )

        return transactionRepository.save(transaction)
    }

    fun addDepositTransaction(
        userPublicId: String,
        taskPublicId: String,
        amount: Int,
        billingCycleId: Long
    ): Transaction {
        val user = userService.findUserByPublicId(userPublicId)
        //TODO: handle exception
        val task = taskService.findTaskByPublicId(taskPublicId)!!
        val billingCycle = billingCycleService.getActiveBillingCycle(user.id)!!

        val transaction = Transaction(
            type = Transaction.Type.Deposit,
            debit = amount,
            credit = 0,
            task = task,
            user = user,
            billingCycle = billingCycle,
        )

        return transactionRepository.save(transaction)
    }

    fun getTransactionsForDateRange(startTime: OffsetDateTime, endTime: OffsetDateTime): List<Transaction> {
        return transactionRepository.findAllTransactionsBetweenDate(startTime, endTime)
    }

    fun addCloseBillingCycleTransaction(user: User, paymentValue: Int): Transaction {
        val transaction = Transaction(
            type = Transaction.Type.Payment,
            user = user,
            debit = paymentValue,
            credit = 0,
            billingCycle = billingCycleService.getActiveBillingCycle(user.id),
        )

        return transactionRepository.save(transaction)
    }
}