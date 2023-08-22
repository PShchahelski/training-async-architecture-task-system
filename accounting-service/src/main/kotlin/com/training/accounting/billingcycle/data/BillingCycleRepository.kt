package com.training.accounting.billingcycle.data

import com.training.accounting.billingcycle.data.model.BillingCycle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BillingCycleRepository : JpaRepository<BillingCycle, Long> {

    @Query(
        value = "SELECT * FROM billing_cycles bc WHERE bc.status = 'Active' and bc.end_datetime is NULL",
        nativeQuery = true
    )
    fun findLastActiveBillingCycle(): BillingCycle?
}