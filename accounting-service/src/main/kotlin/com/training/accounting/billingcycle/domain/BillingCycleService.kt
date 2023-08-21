package com.training.accounting.billingcycle.domain

import com.training.accounting.billingcycle.data.BillingCycleRepository
import com.training.accounting.billingcycle.data.model.BillingCycle
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class BillingCycleService(
    private val repository: BillingCycleRepository,
) {

    val active: BillingCycle?
        get() = repository.findAll()
            .firstOrNull { cycle ->
                cycle.endDatetime == null || cycle.endDatetime > OffsetDateTime.now()
            }

    fun createActiveBillingCycle() {
        val billingCycle = BillingCycle(
            status = BillingCycle.Status.Active,
            startDatetime = OffsetDateTime.now(),
        )

        repository.save(billingCycle)
    }
}