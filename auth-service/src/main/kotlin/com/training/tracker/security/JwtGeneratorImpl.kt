package com.training.tracker.security

import com.training.tracker.data.model.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*


@Service
class JwtGeneratorImpl : JwtGenerator {

    @Value("\${jwt.secret}")
    private lateinit var secretKey: String

    override fun generateAccessToken(user: User): String {
        return Jwts.builder()
            .setSubject(user.email)
            .claim("publicId", user.publicId)
            .setIssuedAt(Date())
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }
}