package com.example.project_study.controller.gallery

import com.example.project_study.data.gall.GalleryForm
import com.example.project_study.data.gall.GalleryImageRepository
import com.example.project_study.data.gall.GalleryRepository
import com.example.project_study.service.GalleryService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
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
        val gallery = galleryService.getGallById(id)
        model.addAttribute("title", gallery.title)
        model.addAttribute("gallery", gallery)
        model.addAttribute("image", galleryService.getAllImageByGallId(id))
        return "content/gallery/detail"
    }

    @GetMapping("/form")
    fun formPage(model:Model):String{
        model.addAttribute("title", "글쓰기")
        return "content/gallery/form"
    }

    @PostMapping("/create")
    fun createForm(form:GalleryForm, @AuthenticationPrincipal user:User):String{
        println(form)//데이터 전송 확인
        galleryService.createGallery(form, user)
        return "redirect:/gall/list"
    }
}