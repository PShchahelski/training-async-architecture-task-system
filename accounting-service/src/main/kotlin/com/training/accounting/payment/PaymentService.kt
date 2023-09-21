package com.training.accounting.payment

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.toResultOr
import com.training.accounting.transaction.domain.TransferService
import com.training.accounting.user.data.model.User
import com.training.accounting.user.domain.UserService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class PaymentService(
	private val userService: UserService,
	private val transferService: TransferService,
	private val thirdPartyNotificationService: ThirdPartyNotificationService,
) {

	fun payUser(userId: Long): Result<Unit, Exception> {
		return userService.findUserById(userId)
			.toResultOr { UsernameNotFoundException("User was not found") }
			.map { user ->
				val balanceToPay = user.balance

				transferService.closeBillingCycle(user)
				tryToZeroBalanceAndSendNotification(balanceToPay, user)

				Ok(Unit)
			}
	}

	private fun tryToZeroBalanceAndSendNotification(balanceToPay: Int, user: User) {
		if (balanceToPay < 0) return

		zeroUserBalanceAfterPayment(user)
		thirdPartyNotificationService.sendNotificationForPayment(user.email, balanceToPay)
	}

	private fun zeroUserBalanceAfterPayment(user: User) {
		user.balance = 0
		userService.updateUser(user)
	}
}