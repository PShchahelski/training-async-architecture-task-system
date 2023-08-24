package com.training.accounting.transaction.data

import com.training.accounting.transaction.data.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long> {

    @Query(
        value = "SELECT * FROM transactions tr WHERE tr.created_at BETWEEN ?1 AND ?2",
        nativeQuery = true
    )
    fun findAllTransactionsBetweenDate(startTime: OffsetDateTime, endTime: OffsetDateTime): List<Transaction>
}