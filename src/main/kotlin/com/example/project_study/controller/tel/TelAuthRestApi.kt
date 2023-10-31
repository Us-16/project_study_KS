package com.example.project_study.controller.tel

import com.example.project_study.service.AccountService
import org.springframework.web.bind.annotation.*

@RestController
class TelAuthRestApi(
    private val accountService: AccountService
) {
    @GetMapping("/sms/tel-dup")
    fun checkDup(@RequestParam("tel", defaultValue = "-1") tel:String): Boolean{
        if(tel == "-1"){
            return false
        }
        return accountService.checkDupTel(tel)
    }

    @GetMapping("/sms/code")
    fun createCode():String{
        return "-1"
    }
}