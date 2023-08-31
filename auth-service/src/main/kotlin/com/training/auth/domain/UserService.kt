package com.training.auth.domain

import com.github.michaelbull.result.Result
import com.training.auth.data.model.User
import org.springframework.stereotype.Service

@Service
interface UserService {

	fun authenticate(email: String, password: String): Result<String, Exception>
	fun register(
		email: String,
		password: String,
		userName: String,
		role: User.Role,
	): Result<String, Exception>

	fun saveUser(user: User): User
}