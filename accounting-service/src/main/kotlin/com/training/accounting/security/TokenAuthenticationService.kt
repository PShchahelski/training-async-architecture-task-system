package com.training.accounting.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken.authenticated
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.function.Function

@Component
class TokenAuthenticationService {

    @Value("\${jwt.secret}")
    private lateinit var secretKey: String

    fun getAuthentication(request: HttpServletRequest): Authentication? {
        val token = getToken(request)
        if (token != null) {
            return authenticated(extractEmail(token), null, emptyList())
        }

        return null
    }

    fun getToken(httpServletRequest: HttpServletRequest): String? {
        return httpServletRequest.getHeader("Authorization")
    }

    private fun hasAuthorizationHeader(request: HttpServletRequest): Boolean {
        return request.getHeader("Authorization") != null
    }

    fun extractEmail(token: String): String {
        return extractClaim(token) { obj: Claims -> obj.subject }
    }

    fun extractAllClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
    }

    fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }
}
