package com.training.accounting.controller

import com.training.accounting.controller.model.AuditLogResponse
import com.training.accounting.controller.model.StatisticsResponse
import com.training.accounting.controller.model.TransactionDto
import com.training.accounting.transaction.domain.TransactionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

@RestController
@RequestMapping("/accounting")
class AccountingController(
    private val transactionService: TransactionService,
) {

    @GetMapping(path = ["/audit/log"])
    fun auditLog(): ResponseEntity<AuditLogResponse> {
        //TODO: handle exception
        val transactions = getTransactionsForToday().map { transaction ->
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

    @GetMapping(path = ["/statistics"])
    fun statistics(): ResponseEntity<StatisticsResponse> {
        val transactions = getTransactionsForToday()
        val debit = transactions.sumOf { transaction -> transaction.debit }
        val credit = transactions.sumOf { transaction -> transaction.credit }

        return ResponseEntity.ok(StatisticsResponse(credit - debit))
    }

    private fun getTransactionsForToday() = transactionService.getTransactionsForDateRange(
        OffsetDateTime.now().truncatedTo(ChronoUnit.DAYS),
        OffsetDateTime.now()
            .withHour(23)
            .withMinute(59)
            .withSecond(59)
    )
}