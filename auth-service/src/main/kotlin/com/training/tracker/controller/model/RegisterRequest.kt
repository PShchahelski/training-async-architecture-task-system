package com.training.tracker.controller.model

import com.training.tracker.data.model.User

data class RegisterRequest(
        val name: String,
        val email: String,
        val password: String,
        val role: User.Role,
)
