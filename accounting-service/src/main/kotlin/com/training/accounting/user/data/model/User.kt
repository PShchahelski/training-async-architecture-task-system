package com.training.accounting.user.data.model

import com.training.accounting.billingcycle.data.model.BillingCycle
import com.training.accounting.task.data.model.Task
import com.training.accounting.transaction.data.model.Transaction
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
class User(
    val email: String,
    val name: String,
    val role: String,
    val publicId: String,
    var balance: Int = 0,
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    val transactions: List<Transaction> = emptyList(),
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    val tasks: List<Task> = emptyList(),
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    val billingCycle: List<BillingCycle> = emptyList(),
    @Id
    @GeneratedValue
    val id: Long = -1,
) : UserDetails {

    constructor() : this("", "", "", "")

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role))
    }

    override fun getPassword(): String {
        return ""
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

}
