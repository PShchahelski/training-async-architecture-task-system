package com.training.accounting.payment

import org.springframework.stereotype.Service

@Service
class ThirdPartyNotificationService {

    fun sendNotificationForPayment(userEmail: String, balanceToPay: Int) = Unit
}