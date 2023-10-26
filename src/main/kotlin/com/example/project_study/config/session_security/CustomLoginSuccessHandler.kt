package com.example.project_study.config.session_security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class CustomLoginSuccessHandler():AuthenticationSuccessHandler {
        override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
            val userAgent = request?.getHeader("User-Agent")
            println(userAgent) //신기한게, 아이폰이랑 맥이랑 똑같이 나옴
    }

}