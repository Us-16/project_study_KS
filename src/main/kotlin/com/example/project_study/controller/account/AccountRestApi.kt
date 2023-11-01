package com.example.project_study.controller.account

import com.example.project_study.service.AccountService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account/api")
class AccountRestApi(
    private val accountService: AccountService
) {
    @GetMapping("/username-dup")
    fun dupUsername(@RequestParam("username") username:String):Boolean{
        return accountService.checkDupUsername(username)
    }
}