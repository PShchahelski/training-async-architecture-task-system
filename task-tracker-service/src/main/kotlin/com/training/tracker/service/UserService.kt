package com.training.tracker.service

import com.training.tracker.data.UserRepository
import com.training.tracker.data.model.User
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun getRandomUser(): User {
        val users = userRepository.findByRole("DEVELOPER")
        val index = Random.nextInt(users.size)
        val user = users[index]
        println("Random User for task: $user")

        return user
    }
}