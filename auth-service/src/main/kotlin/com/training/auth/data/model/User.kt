package com.training.auth.data.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
class User(
        @Column(unique = true)
        val email: String,
        val password: String,
        @Column(name = "name")
        val userName: String,
        @Enumerated(EnumType.STRING)
        val role: Role,
        val uuid: UUID,
        @Id
        @GeneratedValue
        val id: Long,
) {

    constructor() : this("", "", "", Role.DEVELOPER, UUID.randomUUID(), -1)

    enum class Role {
        ADMIN,
        MANAGER,
        DEVELOPER,
        ACCOUNTANT
    }
}