package com.training.auth.controller

import com.training.auth.controller.model.LoginRequest
import com.training.auth.security.JwtGeneratorImpl
import com.training.auth.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/auth")
class AuthorizationController {
    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var jwtGenerator: JwtGeneratorImpl

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<String> {
        return try {
            val user = userService.getUserByEmailAndPassword(request.email, request.password)
                    ?: throw UserNotFoundException("Email or Password is Invalid")

            ResponseEntity<String>(jwtGenerator.generateAccessToken(user), HttpStatus.OK)
        } catch (e: UserNotFoundException) {
            ResponseEntity<String>(e.message, HttpStatus.CONFLICT)
        }
    }
}