package com.training.tracker.data

import com.training.tracker.data.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun findByRole(role: String): List<User>
}