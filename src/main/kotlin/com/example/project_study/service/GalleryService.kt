package com.example.project_study.service

import com.example.project_study.data.gall.Gallery
import com.example.project_study.data.gall.GalleryRepository
import org.springframework.stereotype.Service

@Service
class GalleryService(
    private val galleryRepository: GalleryRepository
) {
    fun getAllList(): MutableList<Gallery> {
        return galleryRepository.findAll()
    }

}