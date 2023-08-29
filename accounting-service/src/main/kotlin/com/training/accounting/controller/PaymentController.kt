package com.training.accounting.controller

import com.training.accounting.payment.PaymentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/payment")
class PaymentController(
    private val paymentService: PaymentService,
) {

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun payment(@PathVariable("userId") userId: Long) {
        paymentService.payUser(userId)
    }
}