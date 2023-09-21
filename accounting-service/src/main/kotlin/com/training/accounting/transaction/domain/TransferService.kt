package com.training.accounting.transaction.domain

import com.training.accounting.billingcycle.domain.BillingCycleService
import com.training.accounting.events.TransactionBusinessEventProducer
import com.training.accounting.task.domain.TaskService
import com.training.accounting.transaction.data.model.Transaction
import com.training.accounting.user.data.model.User
import com.training.accounting.user.domain.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TransferService(
	private val userService: UserService,
	private val taskService: TaskService,
	private val billingCycleService: BillingCycleService,
	private val transactionService: TransactionService,
	private val transactionBusinessEventProducer: TransactionBusinessEventProducer,
) {
	@Transactional
	fun performWithdraw(
		userPublicId: String,
		taskPublicId: String,
		amount: Int,
	) {
		val user = userService.findUserByPublicId(userPublicId)
		val task = taskService.findTaskByPublicId(taskPublicId)
		if (user != null && task != null) {
			val billingCycle = billingCycleService.getActiveBillingCycle(user)
			val transaction = transactionService.addWithdrawTransaction(
				amount,
				task,
				user,
				billingCycle,
			)

			user.balance += transaction.credit
			userService.updateUser(user)
			sendTransactionCompletedEvent(transaction)
		} else {
			// log exception and put message to dlq
		}
	}

	@Transactional
	fun performDeposit(
		userPublicId: String,
		taskPublicId: String,
		amount: Int,
	) {
		val user = userService.findUserByPublicId(userPublicId)
		val task = taskService.findTaskByPublicId(taskPublicId)
		if (user != null && task != null) {
			//TODO: error if user does not exist
			val billingCycle = billingCycleService.getActiveBillingCycle(user)
			val transaction = transactionService.addDepositTransaction(
				amount,
				task,
				user,
				billingCycle,
			)

			user.balance -= transaction.debit
			userService.updateUser(user)
			sendTransactionCompletedEvent(transaction)
		} else {
			// log exception and put message to dlq
		}
	}

	private fun sendTransactionCompletedEvent(transaction: Transaction) {
		transactionBusinessEventProducer.sendTransactionCompleted(transaction)
	}

	fun closeBillingCycle(user: User) {
		val billingCycle = billingCycleService.getActiveBillingCycle(user)
		val transaction = transactionService.addCloseBillingCycleTransaction(user, billingCycle)

		//todo: handle the case when transaction was rollback
		transactionBusinessEventProducer.sendPaymentTransaction(transaction)
		billingCycleService.closeCycle(user)
	}
}