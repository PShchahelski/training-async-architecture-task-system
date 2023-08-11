package com.training.auth.service

import com.training.auth.data.UserRepository
import com.training.auth.data.model.User

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {
    @Autowired
    private lateinit var userRepository: UserRepository
    override fun getUserByEmailAndPassword(email: String, password: String): User? {
        return userRepository.findByEmailAndPassword(email, password)
    }
}