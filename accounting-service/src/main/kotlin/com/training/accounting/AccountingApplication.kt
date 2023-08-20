package com.training.accounting

import com.training.accounting.billingcycle.domain.BillingCycleService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class AccountingApplication {

    @Bean
    fun run(
        billingCycleService: BillingCycleService,
    ): CommandLineRunner {
        return CommandLineRunner { _: Array<String> ->
            if (billingCycleService.active == null) {
                billingCycleService.createActiveBillingCycle()
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<AccountingApplication>(*args)
}
