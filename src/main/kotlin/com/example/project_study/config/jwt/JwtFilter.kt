package com.example.project_study.config.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean

class JwtFilter(
    val AUTHORIZATION_HEADER:String = "Authorization",
    val tokenProvider: TokenProvider
):GenericFilterBean() {
    override fun doFilter(servletRequest: ServletRequest?, servletResponse: ServletResponse?, filterChain: FilterChain?) {
        val httpServletRequest: HttpServletRequest? = servletRequest as HttpServletRequest?
        val jwt:String = resolveToken(httpServletRequest)
        val requestURI = httpServletRequest?.requestId

        if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)){
            val authentication:Authentication = tokenProvider.getAuthentication(jwt)
            SecurityContextHolder.getContext().authentication = authentication
            println("Security Context에 ${authentication.name} 인증정보를 저장했습니다 uri: $requestURI")
        }else{
            println("유효한 JWT 토큰이 없습니다. $requestURI")
        }

        filterChain?.doFilter(servletRequest, servletResponse)
    }

    private fun resolveToken(request: HttpServletRequest?): String {
        val bearerToken = request?.getHeader(AUTHORIZATION_HEADER)
        if (bearerToken != null) {
            if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
                return bearerToken.substring(7)
            }
        }
        return ""
    }
}