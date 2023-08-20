package com.training.accounting.transaction.data

import com.training.accounting.transaction.data.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long> {

    fun findAllByUserPublicId(userPublicId: String): List<Transaction>
}