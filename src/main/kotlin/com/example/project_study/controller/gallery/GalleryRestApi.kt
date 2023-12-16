package com.example.project_study.controller.gallery

import com.example.project_study.data.gall.*
import com.example.project_study.service.GalleryService
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/gall/api", produces = ["application/json;charset=utf8"])
class GalleryRestApi(
    private val galleryService: GalleryService
    ) {
    private val log = LoggerFactory.getLogger(this.javaClass)!!
    @GetMapping("/list")
    fun getAllGall(@RequestParam("page", defaultValue = "0")page:Int, @RequestParam("size", defaultValue = "30")size:Int): Page<Gallery> {
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

    @PostMapping("/test")
    fun test(@RequestBody data: Any):Any {
        println("$data")
        val result = HashMap<String, Any>()
        result["response"] = data
        return result
    }

    @PostMapping("/create")
    fun create(@RequestBody data: ResponseGalleryAndroid): Gallery {
        val galleryForm = GalleryForm(
            title = data.title,
            content = data.content
        )
        return galleryService.createGallery(galleryForm, data.username)
    }


    @PostMapping("/create-image")
    fun createImage(@RequestBody image: MultipartFile){
        println(image.originalFilename)
        return galleryService.createGalleryImage(image, 1009L)
    }

    @GetMapping("/answer-list")
    fun getAllAnswer(@RequestParam(defaultValue = "-1") gallId:Long): List<Answer> {
        return galleryService.getAllAnswerByGallId(gallId)
    }

    @PostMapping("/create/answer")
    fun createAnswer(@RequestBody data:HashMap<String, String>):Long{
        return galleryService.createAnswer(data).id?: -1
    }

    @DeleteMapping("/delete")
    fun deleteGallery(@RequestParam gallId: Long){
        return galleryService.deleteGallery(gallId)
    }
}