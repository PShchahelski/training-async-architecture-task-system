package com.training.tracker.security

import com.training.tracker.domain.CustomerUserDetailsService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.lang.NonNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtAuthenticationFilter(
	private val jwtUtil: JwtUtil,
	private val customerUserDetailsService: CustomerUserDetailsService,
) : OncePerRequestFilter() {

	@Throws(ServletException::class, IOException::class)
	override fun doFilterInternal(
        @NonNull request: HttpServletRequest,
        @NonNull response: HttpServletResponse,
        @NonNull filterChain: FilterChain,
    ) {

		val token = jwtUtil.getToken(request)

		if (token != null) {
			val email: String = jwtUtil.extractUsername(token)
			val userDetails: UserDetails = customerUserDetailsService.loadUserByUsername(email)
			val authentication =
				UsernamePasswordAuthenticationToken(userDetails.username, null, userDetails.authorities)
			println("Authenticated user with email: $email")

			SecurityContextHolder.getContext().authentication = authentication
		}
		filterChain.doFilter(request, response)
	}
}
