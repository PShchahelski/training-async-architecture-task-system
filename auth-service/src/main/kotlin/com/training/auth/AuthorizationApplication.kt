package com.training.auth

import com.training.auth.data.UserRepository
import com.training.auth.data.model.User
import com.training.auth.domain.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootApplication
class AuthorizationApplication {

    @Bean
    fun run(userService: UserService, userRepository: UserRepository, passwordEncoder: PasswordEncoder): CommandLineRunner {
        return CommandLineRunner { _: Array<String> ->
            if (userRepository.findByEmail("admin@gmail.com") == null) {
                userService.saveUser(User("admin@gmail.com", passwordEncoder.encode("adminPassword"), "Admin Admin", User.Role.ADMIN))
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<AuthorizationApplication>(*args)
}
