package com.example.project_study.controller.tel

import com.example.project_study.data.sms.MessageDTO
import com.example.project_study.service.AccountService
import com.example.project_study.service.TelService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
class TelAuthRestApi(
    private val accountService: AccountService,
    private val telService: TelService
) {
    val logger = LoggerFactory.getLogger(TelAuthRestApi::class.java)
    @GetMapping("/sms/tel-dup")
    fun checkDup(@RequestParam("tel", defaultValue = "-1") tel:String): Boolean{
        if(tel == "-1"){
            return false
        }
        return accountService.checkDupTel(tel)
    }

    @GetMapping("/sms/code")
    fun createCode(@RequestParam("tel", defaultValue = "-1")tel:String):String{
        logger.info(tel)
        val dto = MessageDTO(
            to = tel,
            content = telService.createCode()
        )
        logger.info("${dto.content}")
        logger.info("After Send: ${telService.sendSmsCoolSMS(dto)}")
        return dto.content
    }
}