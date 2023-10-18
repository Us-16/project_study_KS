package com.example.project_study.controller.gallery

import com.example.project_study.data.gall.Gallery
import com.example.project_study.service.GalleryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/gall/api")
class GalleryRestApi(
    private val galleryService: GalleryService
) {
    @GetMapping("/list")
    fun getAllGall(): MutableList<Gallery> {
        return galleryService.getAllList()
    }
}