package com.training.tracker.data

import com.training.tracker.data.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

	fun findUserByEmail(email: String): User?
	fun findByRole(role: String): List<User>
}