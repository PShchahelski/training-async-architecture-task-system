package com.training.auth.security

import com.training.auth.data.model.User

interface JwtGenerator {
    fun generateAccessToken(user: User): String
}