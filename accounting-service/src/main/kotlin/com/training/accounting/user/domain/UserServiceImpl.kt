package com.training.accounting.user.domain

import com.training.accounting.user.data.UserRepository
import com.training.accounting.user.data.model.User
import com.training.scheme.registry.streaming.account.v1.UserStreamingPayload
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {
    override fun addUser(payload: UserStreamingPayload) {
        val user = User(
            email = payload.email,
            name = payload.name,
            publicId = payload.publicId,
            role = payload.role,
        )

        userRepository.save(user)
    }

    override fun findUserByEmail(userEmail: String): User {
        return userRepository.findUserByEmail(userEmail)
    }

    override fun findUserByPublicId(userPublicId: String): User {
        return userRepository.findUserByPublicId(userPublicId)
    }
}