package com.training.accounting.payment

import com.training.accounting.transaction.domain.TransferService
import com.training.accounting.user.domain.UserService
import org.springframework.stereotype.Service

@Service
class PaymentService(
    private val userService: UserService,
    private val transferService: TransferService,
    private val thirdPartyNotificationService: ThirdPartyNotificationService,
) {

    fun payUser(userId: Long) {
        val user = userService.findUserById(userId)
        val balanceToPay = user.balance

        transferService.closeBillingCycle(user)
        if (balanceToPay > 0) {
            user.balance = 0

            userService.updateUser(user)
            thirdPartyNotificationService.sendNotificationForPayment(user.email, balanceToPay)
        }
    }
}