package com.training.accounting.controller

import com.training.accounting.controller.model.AuditLogResponse
import com.training.accounting.controller.model.BalanceResponse
import com.training.accounting.controller.model.TransactionDto
import com.training.accounting.user.data.model.User
import com.training.accounting.user.domain.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/employee")
class EmployeeController(
	private val userService: UserService,
) {
	@GetMapping(path = ["/balance"])
	fun balance(): ResponseEntity<BalanceResponse> {
		val user = findUserFromAuthorization()

		return ResponseEntity.ok(BalanceResponse(user.balance))
	}

	@GetMapping(path = ["/audit/log"])
	fun auditLog(): ResponseEntity<AuditLogResponse> {
		val user = findUserFromAuthorization()

		val transactions = user.transactions.map { transaction ->
			TransactionDto(
				type = transaction.type,
				billingCycleId = transaction.billingCycle!!.id,
				amount = transaction.debit - transaction.credit,
				credit = transaction.credit,
				debit = transaction.debit,
				createdAt = transaction.createdAt,
			)
		}

		return ResponseEntity.ok(AuditLogResponse(transactions))
	}

	private fun findUserFromAuthorization(): User {
		val userEmail = extractUserEmailFromAuthorization()

		return userService.findUserByEmail(userEmail)
	}

	private fun extractUserEmailFromAuthorization(): String {
		val authentication = SecurityContextHolder.getContext().authentication

		return authentication.principal as String
	}
}