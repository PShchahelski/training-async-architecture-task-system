package com.training.accounting.security

import com.training.accounting.user.domain.UserService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken.authenticated
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
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
			val user: UserDetails = userService.findUserByEmail(email)

			return authenticated(email, null, user.authorities)
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
