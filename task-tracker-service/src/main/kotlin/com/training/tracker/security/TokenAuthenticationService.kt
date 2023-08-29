package com.training.tracker.security

import com.training.tracker.service.UserService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken.authenticated
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.function.Function

@Component
class TokenAuthenticationService(
	private val userService: UserService,
) {

	@Value("\${jwt.secret}")
	private lateinit var secretKey: String

	fun getAuthentication(request: HttpServletRequest): Authentication? {
		val token = getToken(request)
		if (token != null) {
			val email: String = extractEmail(token)

			return authenticated(email, null, emptyList())
		}

		return null
	}

	fun getToken(httpServletRequest: HttpServletRequest): String? {
		return httpServletRequest.getHeader("Authorization")
	}

	fun extractEmail(token: String): String {
		return extractClaim(token) { obj: Claims -> obj.subject }
	}

	fun extractAllClaims(token: String): Claims {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body
	}

	fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T {
		val claims = extractAllClaims(token)
		return claimsResolver.apply(claims)
	}
}
