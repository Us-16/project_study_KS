package com.example.project_study

import com.example.project_study.data.account.AccountRepository
import com.example.project_study.data.gall.Answer
import com.example.project_study.data.gall.AnswerRepository
import com.example.project_study.data.gall.GalleryRepository
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AnswerUnitTest(
    @Autowired val galleryRepository: GalleryRepository,
    @Autowired val accountRepository: AccountRepository,
    @Autowired val answerRepository: AnswerRepository
) {
    private val log = LoggerFactory.getLogger(this.javaClass)!! //must be checking import
    @Test
    fun create(){
        val answer = Answer(
            content = "Test Answer",
            gallery = galleryRepository.findById(1L).orElseThrow()
        )
        println(answerRepository.save(answer))
    }

    @Test
    fun readById(){
        log.info(answerRepository.findById(1L).orElseThrow().toString())
    }

    @Test
    fun readByGallId(){
        log.info(answerRepository.findByGalleryId(1L).toString())
    }
}