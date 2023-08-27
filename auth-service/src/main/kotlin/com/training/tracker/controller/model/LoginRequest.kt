package com.training.tracker.controller.model

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length

data class LoginRequest(
	@NotNull @Email @Length(min = 5, max = 50)
	val email: String,
	@NotNull @Length(min = 5, max = 10)
	val password: String,
)