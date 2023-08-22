package com.training.accounting.billingcycle.domain

import com.training.accounting.billingcycle.data.BillingCycleRepository
import com.training.accounting.billingcycle.data.model.BillingCycle
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

@Service
class BillingCycleService(
    private val repository: BillingCycleRepository,
) {

    val active: BillingCycle?
        //TODO: handle exception
        get() = repository.findLastActiveBillingCycle()

    fun openBillingCycle() {
        if (active != null) return

        val billingCycle = BillingCycle(
            status = BillingCycle.Status.Active,
            startDatetime = OffsetDateTime.now().truncatedTo(ChronoUnit.DAYS),
        )

        repository.save(billingCycle)
    }
}