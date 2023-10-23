package com.example.project_study.controller.gallery

import com.example.project_study.data.gall.Answer
import com.example.project_study.data.gall.Gallery
import com.example.project_study.data.gall.GalleryImage
import com.example.project_study.service.GalleryService
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Stream

@RestController
@RequestMapping("/gall/api")
class GalleryRestApi(
    private val galleryService: GalleryService,
) {
    @GetMapping("/list")
    fun getAllGall(@RequestParam("page", defaultValue = "0")page:Int, @RequestParam("size", defaultValue = "10")size:Int): Page<Gallery> {
        return galleryService.getAllList(page, size)
    }

    @GetMapping("/detail")
    fun getGallById(@RequestParam(defaultValue = "-1")id:Long): Gallery {
        return galleryService.getGallById(id)
    }

    @GetMapping("/image-list")
    fun getAllGall(@RequestParam(defaultValue = "-1") id:Long): List<GalleryImage> {
        return galleryService.getAllImageByGallId(id)
    }

    @GetMapping("/answer-list")
    fun getAllAnswer(@RequestParam(defaultValue = "-1") id:Long): List<Answer> {
        return galleryService.getAllAnswerByGallId(id)
    }
}