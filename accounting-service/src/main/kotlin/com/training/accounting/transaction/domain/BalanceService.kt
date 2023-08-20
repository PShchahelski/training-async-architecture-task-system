package com.training.accounting.transaction.domain

import com.training.accounting.transaction.domain.model.Balance
import com.training.accounting.user.domain.UserService
import org.springframework.stereotype.Service

@Service
class BalanceService(
    private val userService: UserService,
    private val transactionService: TransactionService,
) {
    fun getBalance(userEmail: String): Balance {
        val user = userService.findUserByEmail(userEmail)
        val transactions = transactionService.findAllByUserId(user.publicId)
            .map { transaction -> transaction.debit to transaction.credit }
            .toList()

        val balance = transactions
            .reduce { debit, credit -> (debit.first + credit.first) to (debit.second + credit.second) }
            .let { it.first - it.second }

        return Balance(
            balance = balance
        )
    }
}