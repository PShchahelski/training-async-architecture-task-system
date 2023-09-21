package com.training.accounting.billingcycle.domain

import com.training.accounting.billingcycle.data.BillingCycleRepository
import com.training.accounting.billingcycle.data.model.BillingCycle
import com.training.accounting.user.data.model.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

@Service
class BillingCycleService(
	private val repository: BillingCycleRepository,
) {
	fun getActiveBillingCycle(user: User): BillingCycle {
		return repository.findLastActiveBillingCycle(user.id) ?: openBillingCycle(user)
	}

	@Transactional
	fun openBillingCycle(user: User): BillingCycle {
		val billingCycle = BillingCycle(
			status = BillingCycle.Status.Active,
			startDatetime = OffsetDateTime.now().truncatedTo(ChronoUnit.DAYS),
			user = user,
		)

		return repository.save(billingCycle)
	}

	@Transactional
	fun closeCycle(user: User): BillingCycle {
		val activeBillingCycle = getActiveBillingCycle(user)
		val closedBillingCycle = BillingCycle(
			id = activeBillingCycle.id,
			status = BillingCycle.Status.Closed,
			endDatetime = OffsetDateTime.now().withHour(23)
				.withMinute(59)
				.withSecond(59),
			startDatetime = activeBillingCycle.startDatetime,
			transactions = activeBillingCycle.transactions,
			user = user,
		)
		repository.save(closedBillingCycle)

		return openBillingCycle(user)
	}
}