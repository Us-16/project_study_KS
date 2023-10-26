package com.example.project_study.config.session_security

import com.example.project_study.data.account.Account
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.RedirectStrategy
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component

@Component
class CustomLoginFailureHandler(
):AuthenticationFailureHandler {
    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?
    ) {
        val redirectStrategy = DefaultRedirectStrategy()
        when(exception){
            is UsernameNotFoundException, is BadCredentialsException -> redirectStrategy.sendRedirect(request, response, "/login?errorCode=BadInputException")
            is AccountExpiredException -> redirectStrategy.sendRedirect(request, response, "/login?errorCode=ExpiredException")
            else -> redirectStrategy.sendRedirect(request, response, "/login?errorCode=UnknownException")
        }
    }
}