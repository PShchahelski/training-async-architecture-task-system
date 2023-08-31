package com.training.auth.domain

import com.training.auth.data.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class CustomerUserDetailsService(
	private val userRepository: UserRepository,
) : UserDetailsService {
	@Throws(UsernameNotFoundException::class)
	override fun loadUserByUsername(email: String): UserDetails {
		return userRepository.findByEmail(email) ?: throw UsernameNotFoundException("User not found !")
	}
}
