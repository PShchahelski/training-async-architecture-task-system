package com.training.tracker.security

import com.github.michaelbull.result.get
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter : OncePerRequestFilter() {
    @Autowired
    private lateinit var tokenAuthenticationService: TokenAuthenticationService

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authenticationResult = tokenAuthenticationService.getAuthentication(request).get()

        SecurityContextHolder.getContext().authentication = authenticationResult

        filterChain.doFilter(request, response)
    }
}
