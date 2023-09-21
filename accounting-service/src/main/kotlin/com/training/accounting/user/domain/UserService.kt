package com.training.accounting.user.domain

import com.training.accounting.user.data.model.User
import com.training.scheme.registry.streaming.account.v1.UserStreamingPayload

interface UserService {

	fun addUser(payload: UserStreamingPayload): User

	fun updateUser(user: User): User

	fun findUserByEmail(userEmail: String): User?

	fun findUserByPublicId(userPublicId: String): User?

	fun findUserById(userId: Long): User?
}