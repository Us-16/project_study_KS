package com.example.project_study.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.DefaultRedirectStrategy
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
            /*agent 요약: 모바일 web -> android / 어플 -> okhttp*/

            if(userAgent?.contains("okhttp") == true){
                response?.contentType="application/json"
                response?.writer?.write(ObjectMapper().writeValueAsString(authentication?.principal as UserDetails))
            }else{
                //TODO("이전 페이지로 돌아가도록 만들어주세요")
                /*급할 거 없이 리팩토링 단계에서 진행하시면 됩니다.*/
                val redirectStrategy = DefaultRedirectStrategy()
                redirectStrategy.sendRedirect(request, response, "/")
            }
        }
}