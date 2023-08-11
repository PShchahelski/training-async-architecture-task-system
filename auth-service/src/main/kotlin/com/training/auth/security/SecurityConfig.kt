//package com.training.auth.security
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.authentication.AuthenticationManager
//import org.springframework.security.config.Customizer
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.config.http.SessionCreationPolicy
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//import org.springframework.security.crypto.password.PasswordEncoder
//import org.springframework.security.web.SecurityFilterChain
//
//
////@Configuration
////@EnableWebSecurity
////class SecurityConfig {
////
////    @Bean
////    @Throws(Exception::class)
////    fun authenticationManager(http: HttpSecurity, noOpPasswordEncoder: NoOpPasswordEncoder?): AuthenticationManager {
////        val authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
////        authenticationManagerBuilder.userDetailsService<UserDetailsService>(userDetailsService).passwordEncoder(noOpPasswordEncoder)
////        return authenticationManagerBuilder.build()
////    }
////
////    @Bean
////    @Throws(Exception::class)
////    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
////        http.csrf().disable()
////                .authorizeRequests()
////                .requestMatchers("/rest/auth/**").permitAll()
////                .anyRequest().authenticated()
////                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////        return http.build()
////    }
////
////    @Suppress("deprecation")
////    @Bean
////    fun passwordEncoder(): NoOpPasswordEncoder {
////        return NoOpPasswordEncoder.getInstance() as NoOpPasswordEncoder
////    }
////}
//
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//class SecurityConfig {
////    private val jwtFilter: JwtFilter? = null
//    @Bean
//    @Throws(Exception::class)
//    fun filterChain(http: HttpSecurity): SecurityFilterChain {
//        http
//                .csrf().disable()
//                .authorizeHttpRequests().requestMatchers("/auth/**").permitAll()
//                .anyRequest().hasRole("USER")
//                .and().sessionManagement().disable()
//                .logout().logoutUrl("/logout")
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
//        return http.build()
//
//
//    }
//
//    @Bean
//    @Throws(java.lang.Exception::class)
//    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
//        http
//                .authorizeHttpRequests { authorizeHttpRequests ->
//                    authorizeHttpRequests
//                            .requestMatchers("/login").hasRole("DEVELOPER")
//                            .requestMatchers("/admin/**").hasRole("ADMIN")
//                }
//
//        return http.build()
//    }
//
//    @Bean
//    @Throws(Exception::class)
//    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
//        return authenticationConfiguration.getAuthenticationManager()
//    }
//
//    @get:Bean
//    val passwordEncoder: PasswordEncoder
//        get() = BCryptPasswordEncoder()
//}