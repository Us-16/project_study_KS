package com.example.project_study

import com.example.project_study.data.account.AccountRepository
import com.example.project_study.data.gall.Gallery
import com.example.project_study.data.gall.GalleryRepository
import org.junit.jupiter.api.Test
import org.junit.platform.commons.logging.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GalleryUnitTest(
    @Autowired val galleryRepository: GalleryRepository,
    @Autowired val accountRepository: AccountRepository
) {
    private val log = LoggerFactory.getLogger(javaClass)!!
    @Test
    fun create(){
        val gall = Gallery(
            account =  accountRepository.findByUsername("test").orElseThrow(),
            title = "Tester",
            content = "first gall",
        )
        println(galleryRepository.save(gall))
    }

    @Test
    fun readById(){
        val gal = galleryRepository.findById(1L).orElseThrow()
        println(gal)
    }

    @Test
    fun readByUsername(){
        val galList = galleryRepository.findByAccountUsername("test")
        println(galList)
    }

    @Test
    fun update(){
        val gal = galleryRepository.findById(1L).orElseThrow()
        gal.title = "Second"
        println(galleryRepository.save(gal))
    }

    @Test
    fun remove(){
        println(galleryRepository.deleteById(2L))
    }
}
