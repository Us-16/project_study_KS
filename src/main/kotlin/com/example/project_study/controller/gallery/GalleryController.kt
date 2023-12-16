package com.example.project_study.controller.gallery

import com.example.project_study.data.gall.GalleryForm
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
    fun listPage(model:Model, @RequestParam(defaultValue = "0")page:Int, @RequestParam(defaultValue = "10") size:Int):String{
        model.addAttribute("title", "리스트")
        model.addAttribute("gall_list", galleryService.getAllList(page, size))
        return "content/gallery/list"
    }

    @GetMapping("/detail")
    fun detailPage(@RequestParam(defaultValue = "-1")id:Long, model: Model, @AuthenticationPrincipal user: User):String{
        val gallery = galleryService.getGallById(id)
        val isMyGall:Boolean = user.username == gallery.account.username
        model.addAttribute("title", gallery.title)
        model.addAttribute("gallery", gallery)
        model.addAttribute("image", galleryService.getAllImageByGallId(id))
        model.addAttribute("isMyGall", isMyGall)
        return "content/gallery/detail"
    }

    //TODO("null처리 때문에 후에 다 뜯어내야할 것으로 보입니다")
    //현시점에서는 해결되는 수준에서만 작성
    @GetMapping("/form")
    fun formPage(model:Model, @RequestParam(defaultValue = "-1") id:Long, @AuthenticationPrincipal user:User):String{
        val title = if(id==-1L) "글쓰기" else{
            val data = galleryService.getGallById(id)
            model.addAttribute("gall_data", data)
            "수정"
        }
        model.addAttribute("title", title)
        model.addAttribute("id", id)
        return "content/gallery/form"
    }

    @PostMapping("/create")
    fun createForm(form:GalleryForm, @AuthenticationPrincipal user:User):String{
        galleryService.createGallery(form, user.username)
        return "redirect:/gall/list"
    }

    @PostMapping("/update")
    fun updateForm(id: Long, form: GalleryForm, @AuthenticationPrincipal user:User):String{
        return "redirect:/gall/detail?id=${galleryService.updateGallery(id, form, user.username).id}"
    }

    @GetMapping("/remove")
    fun deleteForm(@RequestParam id: Long):String{
        galleryService.removeGallery(id)
        return "redirect:/gall/list"
    }
}