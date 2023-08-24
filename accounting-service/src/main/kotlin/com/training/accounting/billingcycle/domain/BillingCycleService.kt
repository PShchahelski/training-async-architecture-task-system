package com.training.accounting.billingcycle.domain

import com.training.accounting.billingcycle.data.BillingCycleRepository
import com.training.accounting.billingcycle.data.model.BillingCycle
import com.training.accounting.user.data.model.User
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

    fun openBillingCycle(user: User) {
        val billingCycle = BillingCycle(
            status = BillingCycle.Status.Active,
            startDatetime = OffsetDateTime.now().truncatedTo(ChronoUnit.DAYS),
            user = user,
        )

        repository.save(billingCycle)
    }
}