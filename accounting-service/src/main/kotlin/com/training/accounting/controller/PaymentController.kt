package com.training.accounting.controller

import com.github.michaelbull.result.mapBoth
import com.training.accounting.payment.PaymentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/payment")
class PaymentController(
	private val paymentService: PaymentService,
) {

	@GetMapping("/{userId}")
	fun payment(@PathVariable("userId") userId: Long) {
		paymentService.payUser(userId).mapBoth(
			success = { ResponseEntity.noContent() },
			failure = { exception -> ResponseEntity(exception.message, HttpStatus.NOT_FOUND) }
		)
	}
}