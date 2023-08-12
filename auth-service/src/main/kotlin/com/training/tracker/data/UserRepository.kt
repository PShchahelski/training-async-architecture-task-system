package com.training.tracker.data

import com.training.tracker.data.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmailAndPassword(email: String, password: String): User?

    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): User?
}