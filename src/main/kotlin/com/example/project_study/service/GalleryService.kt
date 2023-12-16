package com.example.project_study.service

import com.example.project_study.data.account.AccountRepository
import com.example.project_study.data.gall.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap
import kotlin.jvm.optionals.getOrNull

@Service
class GalleryService(
    private val accountRepository: AccountRepository,
    private val galleryRepository: GalleryRepository,
    private val galleryImageRepository: GalleryImageRepository,
    private val answerRepository: AnswerRepository
) {
    fun getAllList(page:Int, size:Int): Page<Gallery> {
        val pageRequest = PageRequest.of(page, size)
        return galleryRepository.findAllByOrderByIdDesc(pageRequest)
    }

    fun getGallById(id:Long): Gallery {
        val gall = galleryRepository.findById(id).orElseThrow()
        gall.account.password = "***"
        return gall
    }

    fun getAllImageByGallId(gallId: Long): List<GalleryImage> {
        return galleryImageRepository.findByGalleryId(gallId)
    }

    fun getAllAnswerByGallId(gallId: Long): List<Answer> {
        return answerRepository.findByGalleryId(gallId)
    }

    fun createGallery(form:GalleryForm, username: String): Gallery {
        val gallery = Gallery(
            account = accountRepository.findByUsername(username).get(),
            title = form.title,
            content = form.content,
            classify = "게시판"
        )
        val result = galleryRepository.save(gallery)

        if(form.image?.isEmpty == false) {
            createGalleryImage(form.image,result.id!!)
        }

        return result
    }

    fun updateGallery(id: Long, form:GalleryForm, username:String):Gallery{
        val oriGall = galleryRepository.findById(id).getOrNull()
        oriGall!!.title = form.title
        oriGall.content = form.content
        oriGall.modifiedDate = LocalDateTime.now()

        return galleryRepository.save(oriGall)
    }

    fun createGalleryImage(image:MultipartFile, galleryId:Long){
        val projectPath:String = System.getProperty("user.dir") + "/src/main/resources/static/img/gallery"
        val uuid = UUID.randomUUID()
        val fileName = uuid.toString() + "_" + image.originalFilename

        val saveFile = File(projectPath, fileName)
        image.transferTo(saveFile)

        val galleryImage = GalleryImage(
            gallery = galleryRepository.findById(galleryId).get(),
            path = "/img/gallery/$fileName",
            size = image.size
        )
        galleryImageRepository.save(galleryImage)
    }

    fun createAnswer(answer: Answer): Answer {
        return answerRepository.save(answer)
    }
    fun createAnswer(data:HashMap<String, String>): Answer {
        val answer = Answer(
            content = data["content"]!!,
            createdDate = LocalDateTime.now(),
            gallery =  galleryRepository.findById(data["gallId"]!!.toLong()).get(),
            account = accountRepository.findByUsername(data["username"]!!).get()
        )
        return answerRepository.save(answer)
    }

    fun removeGallery(id: Long) {
        return galleryRepository.delete(galleryRepository.findById(id).orElseThrow())
    }

    fun deleteGallery(gallId: Long) {
        return galleryRepository.deleteById(gallId)
    }
}