package com.training.auth.controller.model

import com.training.auth.data.model.User

data class RegisterRequest(
	val name: String,
	val email: String,
	val password: String,
	val role: User.Role,
)
