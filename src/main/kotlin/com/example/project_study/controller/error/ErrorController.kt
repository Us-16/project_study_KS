package com.example.project_study.controller.error

import jakarta.servlet.RequestDispatcher
import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class ErrorController: ErrorController {
    @RequestMapping("/error")
    fun errorHandle(request:HttpServletRequest):String{
        val status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) as Int?
        var errorPath = "/content/error/"

        val statusCode = Integer.parseInt(status.toString())
        errorPath += when(statusCode){
            HttpStatus.FORBIDDEN.value() -> "forbidden"
            HttpStatus.NOT_FOUND.value() -> "notfound"
            else -> "error"
        }

        return errorPath
    }
}