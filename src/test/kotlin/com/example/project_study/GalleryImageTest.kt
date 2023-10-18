package com.example.project_study

import com.example.project_study.data.gall.GalleryImage
import com.example.project_study.data.gall.GalleryImageRepository
import com.example.project_study.data.gall.GalleryRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GalleryImageTest(
    @Autowired val galleryRepository: GalleryRepository,
    @Autowired val galleryImageRepository: GalleryImageRepository
) {
    @Test
    fun create(){
        val image = GalleryImage(
            gallery = galleryRepository.findById(1L).orElseThrow(), //이로써 연결이 되어 있다는 것을 증명해냄
            path = "/test/test2.png",
            size = 40
        )
        println(galleryImageRepository.save(image))
    }

    @Test
    fun readById(){
        val image = galleryImageRepository.findById(1L)
        println(image.orElseThrow())
    }

    @Test
    fun readByGalleryId(){
        val imageList = galleryImageRepository.findByGalleryId(1L)
        imageList.forEach { println(it) }
    }

    @Test
    fun update(){
        val image = galleryImageRepository.findById(2L).orElseThrow()
        image.size = 60
        println(galleryImageRepository.save(image))
    }

    @Test
    fun remove(){
        println(galleryImageRepository.deleteById(2L))
    }
}