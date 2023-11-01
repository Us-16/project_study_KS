package com.example.project_study.service

import com.example.project_study.data.account.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountRepository: AccountRepository
) {
    fun checkDupTel(tel:String):Boolean{
        return accountRepository.findByTel(tel).isEmpty //비어있다면 true
    }

    fun checkDupUsername(username:String):Boolean{
        return accountRepository.findByUsername(username).isEmpty
    }
}