package com.training.tracker.domain

import com.training.tracker.data.model.User
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
interface UserService {
    @Throws(UsernameNotFoundException::class)
    fun authenticate(email: String, password: String): String
    fun register(
            email: String,
            password: String,
            userName: String,
            role: User.Role,
    ): ResponseEntity<*>

    fun saverUser(user: User): User
}