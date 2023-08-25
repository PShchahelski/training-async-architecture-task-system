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

    fun getActiveBillingCycle(userId: Long): BillingCycle? {
        //TODO: handle exception
        return repository.findLastActiveBillingCycle(userId)
    }

    fun openBillingCycle(user: User) {
        val billingCycle = BillingCycle(
            status = BillingCycle.Status.Active,
            startDatetime = OffsetDateTime.now().truncatedTo(ChronoUnit.DAYS),
            user = user,
        )

        repository.save(billingCycle)
    }

    fun closeCycle(user: User) {
        val billingCycle = getActiveBillingCycle(user.id)

        billingCycle?.apply {
            status = BillingCycle.Status.Closed
            endDatetime = OffsetDateTime.now().withHour(23)
                .withMinute(59)
                .withSecond(59)

            repository.save(this)
        }

        openBillingCycle(user)
    }
}