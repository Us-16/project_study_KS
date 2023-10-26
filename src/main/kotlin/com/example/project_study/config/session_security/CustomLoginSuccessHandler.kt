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
            /*agent 요약: 모바일 web -> android / 어플 -> okhttp*/
            println(userAgent) //신기한게, 아이폰이랑 맥이랑 똑같이 나옴

            if(userAgent?.contains("Android") == true){
                println("Access By Android")
            }else if (userAgent?.contains("okhttp") == true){
                println("Access By Application with OkHttp3")
            }else{
                println("Access by Other")
            }
    }

}