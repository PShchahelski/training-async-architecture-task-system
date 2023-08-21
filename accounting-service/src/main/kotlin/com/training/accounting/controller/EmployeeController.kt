package com.training.accounting.controller

import com.training.accounting.controller.model.BalanceResponse
import com.training.accounting.transaction.domain.BalanceService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/employee")
class EmployeeController(
    private val balanceService: BalanceService,
) {

    @GetMapping(path = ["/balance"])
    fun balance(): ResponseEntity<BalanceResponse> {
        val authentication = SecurityContextHolder.getContext().authentication



        return ResponseEntity.ok(
            BalanceResponse(balanceService.getBalance(authentication.principal as String).balance)
        )
    }
}