package com.training.auth.data

import com.training.auth.data.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmailAndPassword(email: String, password: String): User?
}