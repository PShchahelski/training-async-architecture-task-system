package com.training.accounting.billingcycle.data

import com.training.accounting.billingcycle.data.model.BillingCycle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BillingCycleRepository : JpaRepository<BillingCycle, Long>