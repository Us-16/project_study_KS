package com.example.project_study.data.gall

import org.springframework.data.jpa.repository.JpaRepository

interface AnswerRepository:JpaRepository<Answer, Long> {
    fun findByGalleryId(id:Long):List<Answer>
}