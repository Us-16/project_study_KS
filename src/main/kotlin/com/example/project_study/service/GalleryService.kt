package com.example.project_study.service

import com.example.project_study.data.gall.*
import org.springframework.stereotype.Service
import java.util.*

@Service
class GalleryService(
    private val galleryRepository: GalleryRepository,
    private val galleryImageRepository: GalleryImageRepository,
    private val answerRepository: AnswerRepository
) {
    fun getAllList(): MutableList<Gallery> {
        return galleryRepository.findAll()
    }

    fun getGallById(id:Long): Gallery {
        return galleryRepository.findById(id).orElseThrow()
    }

    fun getAllImageByGallId(gallId: Long): List<GalleryImage> {
        return galleryImageRepository.findByGalleryId(gallId)
    }

    fun getAllAnswerByGallId(gallId: Long): List<Answer> {
        return answerRepository.findByGalleryId(gallId)
    }
}