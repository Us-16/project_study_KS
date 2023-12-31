package com.example.project_study.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MainController {
    @GetMapping("/first")
    fun firstPage(): String{
        return "content/first"
    }

    @GetMapping("/main")
    fun mainPage(model:Model):String {
        return "content/main"
    }
}