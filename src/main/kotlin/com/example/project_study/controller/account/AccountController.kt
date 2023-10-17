package com.example.project_study.controller.account

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AccountController {
    @GetMapping("/account")
    fun accountPage(): String{
        return "content/account/account"
    }

    @GetMapping("/login")
    fun loginPage(): String{
        return "content/account/login"
    }
}