package com.training.tracker.domain

import com.training.tracker.data.UserRepository
import com.training.tracker.data.model.User
import com.training.tracker.security.JwtGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var jwtGenerator: JwtGenerator

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    override fun authenticate(email: String, password: String): String {
        val authentication: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        /* principal = */ email,
                        /* credentials = */ password
                )
        )
        SecurityContextHolder.getContext().authentication = authentication

        val user: User = userRepository.findByEmail(authentication.name)
                ?: throw UsernameNotFoundException("User not found")

        return jwtGenerator.generateAccessToken(user)
    }

    override fun register(
            email: String,
            password: String,
            userName: String,
            role: User.Role,
    ): ResponseEntity<*> {
        return if (userRepository.existsByEmail(email)) {
            ResponseEntity<String>("email is already taken !", HttpStatus.SEE_OTHER)
        } else {
            val user = User(email, passwordEncoder.encode(password), userName, role)
            userRepository.save(user)

            val token = jwtGenerator.generateAccessToken(user)

            ResponseEntity<Any>(token, HttpStatus.OK)
        }
    }

    override fun saverUser(user: User): User {
        return userRepository.save(user)
    }
}