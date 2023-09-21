package com.training.accounting.transaction.domain

import com.training.accounting.billingcycle.data.model.BillingCycle
import com.training.accounting.task.data.model.Task
import com.training.accounting.transaction.data.TransactionRepository
import com.training.accounting.transaction.data.model.Transaction
import com.training.accounting.user.data.model.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Service
class TransactionService(
	private val transactionRepository: TransactionRepository,
) {
	@Transactional
	fun addWithdrawTransaction(
		amount: Int,
		task: Task,
		user: User,
		billingCycle: BillingCycle,
	): Transaction {
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

	@Transactional
	fun addDepositTransaction(
		amount: Int,
		task: Task,
		user: User,
		billingCycle: BillingCycle,
	): Transaction {
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

	@Transactional
	fun addCloseBillingCycleTransaction(user: User, billingCycle: BillingCycle): Transaction {
		val transaction = Transaction(
			type = Transaction.Type.Payment,
			user = user,
			debit = user.balance,
			credit = 0,
			billingCycle = billingCycle,
		)

		return transactionRepository.save(transaction)
	}
}