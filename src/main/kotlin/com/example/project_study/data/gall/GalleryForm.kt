package com.example.project_study.data.gall

import org.springframework.web.multipart.MultipartFile

data class GalleryForm(
    val title:String,
    val content:String,
    val image: MultipartFile
)
