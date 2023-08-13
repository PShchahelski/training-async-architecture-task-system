package com.training.tracker.data.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User(
        val email: String,
        val name: String,
        val role: String,
        val publicId: String,
        @Id
        @GeneratedValue
        val id: Long = -1,
)