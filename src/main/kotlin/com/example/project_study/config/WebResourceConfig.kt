package com.example.project_study.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebResourceConfig: WebMvcConfigurer {
    private val connectPath:String = "/img/**"
    private val resourcePath:String = "file:///D:/project_study_KS/src/main/resources/static/img/"
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler(connectPath).addResourceLocations(resourcePath)
    }
    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addRedirectViewController("/", "/main")
    }
}