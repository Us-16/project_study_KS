package com.example.project_study.data.gall

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface GalleryRepository:JpaRepository<Gallery, Long> {
    fun findByAccountUsername(username:String): List<Gallery>
    fun findByClassify(classify: String): List<Gallery>
    fun findAllByOrderByIdDesc(pageable: Pageable):Page<Gallery>
}