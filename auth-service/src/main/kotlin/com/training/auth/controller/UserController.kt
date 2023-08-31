package com.training.auth.controller

import com.github.michaelbull.result.mapBoth
import com.training.auth.controller.model.LoginRequest
import com.training.auth.domain.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/user")
class UserController(
	private val userService: UserService,
) {

	//RessourceEndPoint:http://localhost:9080/api/user/authenticate
	@PostMapping("/authenticate")
	fun login(@RequestBody loginDto: LoginRequest): ResponseEntity<String> {
		return userService.authenticate(
			email = loginDto.email,
			password = loginDto.password,
		).mapBoth(
			success = { token -> ResponseEntity.ok(token) },
			failure = { exception -> ResponseEntity(exception.message, HttpStatus.NOT_FOUND) }
		)
	}
}