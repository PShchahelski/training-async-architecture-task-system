package com.training.tracker.data.model

import com.training.tracker.events.model.UserCreatedEvent
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Entity
@Table(name = "users")
class User(
        @Column(unique = true)
        val email: String,
        private var password: String,
        @Column(name = "name")
        val name: String,
        @Enumerated(EnumType.STRING)
        val role: Role = Role.DEVELOPER,
        val uuid: UUID = UUID.randomUUID(),
        @Id
        @GeneratedValue
        val id: Long = -1,
) : UserDetails {

    constructor() : this("", "", "")

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role.toString()))
    }

    override fun getPassword(): String {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    enum class Role {
        ADMIN,
        MANAGER,
        DEVELOPER,
        ACCOUNTANT
    }
}

fun User.toUserCreatedEventDto(): UserCreatedEvent {
    return UserCreatedEvent(email, name, role.toString(), uuid.toString())
}