package com.example.project_study.config.session_security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
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
            //println(userAgent) //신기한게, 아이폰이랑 맥이랑 똑같이 나옴

            if(userAgent?.contains("okhttp") == true){
                //TODO("이게 맞다면, 유저정보 일부를 reponse하도록 만들어주세요")
                response?.contentType="application/json"
                response?.writer?.write(authentication?.principal.toString())
            }else{
                //TODO("이전 페이지로 돌아가도록 만들어주세요")
                /*급할 거 없이 리팩토링 단계에서 진행하시면 됩니다.*/
                val redirectStrategy = DefaultRedirectStrategy()
                redirectStrategy.sendRedirect(request, response, "/")
            }
        }

}