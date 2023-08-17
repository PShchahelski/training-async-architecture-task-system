package com.training.tracker.security

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken.authenticated
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Component
class TokenAuthenticationService {

    fun getAuthentication(request: HttpServletRequest): Authentication? {
        if (hasAuthorizationHeader(request)) {
            return authenticated(null, null, emptyList())
        }

        return null
    }

    private fun hasAuthorizationHeader(request: HttpServletRequest): Boolean {
        return request.getHeader("Authorization") != null
    }
}
