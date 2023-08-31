package com.training.auth.domain

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.training.auth.data.UserRepository
import com.training.auth.data.model.User
import com.training.auth.events.UserEventProducer
import com.training.auth.security.JwtGenerator
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
	private val passwordEncoder: PasswordEncoder,
	private val jwtGenerator: JwtGenerator,
	private val userRepository: UserRepository,
	private val authenticationManager: AuthenticationManager,
	private val userEventProducer: UserEventProducer,
) : UserService {

	override fun authenticate(email: String, password: String): Result<String, Exception> {
		val authentication: Authentication = authenticationManager.authenticate(
			UsernamePasswordAuthenticationToken(
				/* principal = */ email,
				/* credentials = */ password
			)
		)

		return if (authentication.isAuthenticated) {
			val user = userRepository.findByEmail(authentication.name)
			if (user != null) {
				Ok(jwtGenerator.generateAccessToken(user))
			} else {
				Err(UsernameNotFoundException("User not found"))
			}
		} else {
			Err(RuntimeException("User is not authenticated"))
		}
	}

	override fun register(
		email: String,
		password: String,
		userName: String,
		role: User.Role,
	): Result<String, Exception> {
		return if (userRepository.existsByEmail(email)) {
			Err(Exception("Email is already taken !"))
		} else {
			val user = User(email, passwordEncoder.encode(password), userName, role)
			val dbUser = saveUser(user)
			val token = jwtGenerator.generateAccessToken(dbUser)

			Ok(token)
		}
	}

	@Transactional
	override fun saveUser(user: User): User {
		return userRepository.save(user)
			.also(userEventProducer::sendUserCreated)
	}
}