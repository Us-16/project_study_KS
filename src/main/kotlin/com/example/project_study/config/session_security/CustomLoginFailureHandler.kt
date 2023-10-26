package com.example.project_study.config.session_security

import com.example.project_study.data.account.Account
import com.fasterxml.jackson.databind.ObjectMapper
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
        val userAgent = request?.getHeader("User-Agent")
        if(userAgent?.contains("okhttp") == true){
            //TODO("모바일에서 접속 실패 관련 로직 필요")
            response?.contentType="application/json"
            response?.writer?.write(ObjectMapper().writeValueAsString(failureMsg(exception)))
        }else{
            val redirectStrategy = DefaultRedirectStrategy()
            when(exception){
                is UsernameNotFoundException, is BadCredentialsException -> redirectStrategy.sendRedirect(request, response, "/login?errorCode=BadInputException")
                is AccountExpiredException -> redirectStrategy.sendRedirect(request, response, "/login?errorCode=ExpiredException")
                else -> redirectStrategy.sendRedirect(request, response, "/login?errorCode=UnknownException")
            }
        }
    }

    private fun failureMsg(exception:AuthenticationException?): HashMap<String, String> {
        val message = HashMap<String, String>()
        when(exception){
            is UsernameNotFoundException, is BadCredentialsException -> message["code"] = "BadInputException"
            is AccountExpiredException -> message["code"] = "ExpiredException"
            else -> message["code"] = "UnknownException"
        }
        return message
    }
}