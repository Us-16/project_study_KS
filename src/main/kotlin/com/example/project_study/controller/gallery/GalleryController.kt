package com.example.project_study.controller.gallery

import com.example.project_study.data.gall.GalleryImageRepository
import com.example.project_study.data.gall.GalleryRepository
import com.example.project_study.service.GalleryService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/gall")
class GalleryController(
    private val galleryService: GalleryService
) {
    @GetMapping("/list")
    fun listPage(model:Model):String{
        model.addAttribute("title", "리스트")
        return "content/gallery/list"
    }

    @GetMapping("/detail")
    fun detailPage(@RequestParam(defaultValue = "-1")id:Long, model: Model):String{
        val gallery = galleryService
        model.addAttribute("title", )
        return "content/gallery/detail"
    }
}