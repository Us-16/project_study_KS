package com.example.project_study.config

import com.example.project_study.data.account.AccountRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import java.lang.RuntimeException

@Component
class CustomUserDetailsService(
    private val accountRepository: AccountRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val siteUser = username?.let { accountRepository.findByUsername(it) }

        if(siteUser!!.isEmpty) throw UsernameNotFoundException("user not found")


        val authorities = mutableListOf<SimpleGrantedAuthority>()
        when(siteUser.get().role){
            "USER" -> authorities.add(SimpleGrantedAuthority("USER"))
            "ADMIN" -> authorities.add(SimpleGrantedAuthority("ADMIN"))
            else -> throw RuntimeException("store error")
        }

        return User(siteUser.get().username, siteUser.get().password, authorities)
    }
}