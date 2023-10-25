package com.example.project_study.config.jwt

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import java.security.Key
import java.time.LocalDateTime
import java.util.Arrays
import java.util.Date
import java.util.stream.Collectors
import java.util.stream.Stream

@Component
class TokenProvider(
    private val logger: Logger? = LoggerFactory.getLogger(TokenProvider::class.java),
    private val AUTHORITIES_KEY: String = "auth",
    private var secret:String,
    private var tokenValidityInMilliseconds:Long,
    private var key:Key
):InitializingBean {
    fun TokenProvider(
        @Value("#{jwt.secret}") secret: String,
        @Value("#{jwt.token-validty-in-seconds}") tokenValidityInMilliseconds: Long
    ){
        this.secret = secret
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds * 1000
    }

    override fun afterPropertiesSet() {
        val keyBytes = Decoders.BASE64.decode(secret) //TODO(이후에 AES256으로 변경바랍니다.)
        this.key = Keys.hmacShaKeyFor(keyBytes)
    }

    /**
     * 토큰 생성
     */
    fun createToken(
        authentication:Authentication
    ):String{
        val authorities: String = authentication.authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","))
        val now:Long = Date().time
        val validity = Date(now + this.tokenValidityInMilliseconds)

        return Jwts.builder()
            .setSubject(authentication.name)
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .compact()
    }

    fun getAuthentication(token:String):Authentication{
        val claims = Jwts
            .parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

        val authorities:Collection<GrantedAuthority> = claims[AUTHORITIES_KEY].toString().split(",")
            .map { authority -> SimpleGrantedAuthority(authority) }
            .toList()

        val principal:User = User(claims.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    fun validateToken(token:String):Boolean{
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        }catch (e:SecurityException){
            logger?.info("잘못된 JWT 서명입니다.")
        }catch (e:MalformedJwtException){
            logger?.info("잘못된 JWT 서명입니다.")
        }catch (e:ExpiredJwtException){
            logger?.info("만료된 JWT 토큰입니다.")
        }catch (e:IllegalArgumentException){
            logger?.info("JWT 토큰이 잘못되었습니다.")
        }

        return false
    }
}