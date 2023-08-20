package com.training.accounting.user.data.model

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
    val balance: Int = 0,
    @Id
    @GeneratedValue
    val id: Long = -1,
)

fun User.copy(
    email: String = this.email,
    name: String = this.name,
    role: String = this.role,
    publicId: String = this.publicId,
    balance: Int = this.balance,
    id: Long = this.id,
): User {
    return User(
        email, name, role, publicId, balance, id
    )
}