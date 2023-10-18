package com.example.project_study.config

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

class WebResourceConfig: WebMvcConfigurer {
    private val connectPath:String = "/images/**"
    private val resourcePath:String = "file:///Users/janghyolim/code/project_study/src/main/resources/static/img/"

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler(connectPath).addResourceLocations(resourcePath)
    }
}