package com.training.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


//@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
@SpringBootApplication
class AuthorizationApplication

fun main(args: Array<String>) {
    runApplication<AuthorizationApplication>(*args)
}
