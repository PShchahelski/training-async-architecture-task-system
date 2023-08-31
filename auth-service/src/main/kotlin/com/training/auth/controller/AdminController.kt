package com.training.auth.controller

import com.github.michaelbull.result.mapBoth
import com.training.auth.controller.model.RegisterRequest
import com.training.auth.domain.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminController(
	private val userService: UserService,
) {

	//RessourceEndPoint:http://localhost:9080/api/admin/register
	@PostMapping("/register")
	fun register(@RequestBody registerDto: RegisterRequest): ResponseEntity<String> {
		return userService.register(
			email = registerDto.email,
			password = registerDto.password,
			userName = registerDto.name,
			role = registerDto.role,
		).mapBoth(
			success = { token -> ResponseEntity.ok(token) },
			failure = { exception -> ResponseEntity(exception.message, HttpStatus.SEE_OTHER) },
		)
	}
}