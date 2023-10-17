package com.example.project_study.data.gall

import org.springframework.data.jpa.repository.JpaRepository

interface GalleryRepository:JpaRepository<Gallery, Long> {
    fun findByAccountUsername(username:String): List<Gallery>
}