package com.example.project_study.service

import com.example.project_study.data.account.Account
import com.example.project_study.data.account.AccountRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun checkDupTel(tel:String):Boolean{
        return accountRepository.findByTel(tel).isEmpty //비어있다면 true
    }

    fun checkDupUsername(username:String):Boolean{
        if(username.isEmpty())
            return false
        return accountRepository.findByUsername(username).isEmpty
    }

    fun createAccount(account: Account): Boolean {
        account.createDate = LocalDateTime.now()
        account.password = passwordEncoder.encode(account.password)
        return accountRepository.save(account).id!! > 0
    }
}