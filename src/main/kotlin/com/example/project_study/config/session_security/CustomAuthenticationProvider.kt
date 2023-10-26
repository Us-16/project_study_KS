package com.example.project_study.config.session_security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationProvider(
    private val passwordEncoder: PasswordEncoder,
    private val customUserDetailsService: CustomUserDetailsService
): AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication {
        println("provider")
        val username = authentication!!.name
        val password = authentication.credentials.toString()
        val user = customUserDetailsService.loadUserByUsername(username)

        if(!passwordEncoder.matches(password, user.password)) throw BadCredentialsException("Invalid user Password")

        return UsernamePasswordAuthenticationToken(user, user.password, user.authorities)
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication?.let { UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(it) }!!
    }
}