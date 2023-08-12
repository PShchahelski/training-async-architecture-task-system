package com.training.tracker.security

import com.training.tracker.data.model.User

interface JwtGenerator {
    fun generateAccessToken(user: User): String
}