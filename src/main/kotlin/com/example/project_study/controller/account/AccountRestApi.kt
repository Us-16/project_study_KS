package com.example.project_study.controller.account

import com.example.project_study.data.account.Account
import com.example.project_study.service.AccountService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/account/api")
class AccountRestApi(
    private val accountService: AccountService
) {
    @GetMapping("/username-dup")
    fun dupUsername(@RequestParam("username") username:String):Boolean{
        return accountService.checkDupUsername(username)
    }

    @PostMapping("/create")
    fun createAccount(@RequestBody account:Account): Boolean {
        return accountService.createAccount(account)
    }
}