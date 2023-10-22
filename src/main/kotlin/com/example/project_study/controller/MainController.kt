package com.example.project_study.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainController {
    @GetMapping("/first")
    fun firstPage(): String{
        return "content/first"
    }

    @GetMapping("/main")
    fun mainPage(model:Model):String{
        return "content/main"
    }
}