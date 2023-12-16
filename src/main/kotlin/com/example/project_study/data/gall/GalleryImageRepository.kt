package com.example.project_study.data.gall

import org.springframework.data.jpa.repository.JpaRepository

interface GalleryImageRepository:JpaRepository<GalleryImage, Long> {
    fun findByGalleryId(id:Long): List<GalleryImage>
}