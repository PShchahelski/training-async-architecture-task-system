package com.training.tracker.service

import com.training.tracker.data.UserRepository
import com.training.tracker.data.model.User
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class UserService(
	private val userRepository: UserRepository,
) {
	fun getRandomUser(): User {
		val users = userRepository.findByRole("DEVELOPER")
		val index = Random.nextInt(users.size)

		// todo: can be empty list and should retry later
		return users[index]
	}

	fun findUserByEmail(userEmail: String): User? {
		return userRepository.findUserByEmail(userEmail)
	}
}