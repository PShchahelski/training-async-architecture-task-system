package com.training.auth.data

import com.training.auth.data.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
	fun existsByEmail(email: String): Boolean

	fun findByEmail(email: String): User?
}