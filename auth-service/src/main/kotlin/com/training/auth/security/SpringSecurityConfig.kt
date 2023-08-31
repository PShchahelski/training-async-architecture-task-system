package com.training.auth.security

import com.training.auth.data.UserRepository
import com.training.auth.data.model.User
import com.training.auth.domain.CustomerUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity(debug = true)
class SpringSecurityConfig(
	private val userRepository: UserRepository,
	private val jwtAuthenticationFilter: JwtAuthenticationFilter,
) {

	@Bean
	@Throws(Exception::class)
	fun filterChain(http: HttpSecurity): SecurityFilterChain {
		return http
			.authorizeHttpRequests { authorizeHttpRequests ->
				authorizeHttpRequests
					.requestMatchers("/user/**").permitAll()
					.requestMatchers("/admin/**").hasAuthority(User.Role.ADMIN.name)
			}
			.csrf { csrf -> csrf.disable() }
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
			.build()
	}

	@Bean
	@Throws(Exception::class)
	fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
		return authenticationConfiguration.getAuthenticationManager()
	}

	@Bean
	fun authenticationProvider(): AuthenticationProvider {
		return DaoAuthenticationProvider()
			.apply {
				setUserDetailsService(userDetailsService())
				setPasswordEncoder(passwordEncoder())
			}
	}

	@Bean
	fun userDetailsService(): UserDetailsService {
		return CustomerUserDetailsService(userRepository)
	}

	@Bean
	fun passwordEncoder(): PasswordEncoder {
		return BCryptPasswordEncoder()
	}
}
