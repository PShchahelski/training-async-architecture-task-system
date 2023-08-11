package com.training.auth.service

import com.training.auth.data.model.User
import org.springframework.stereotype.Service

@Service
interface UserService {
    fun getUserByEmailAndPassword(email: String, password: String): User?
}