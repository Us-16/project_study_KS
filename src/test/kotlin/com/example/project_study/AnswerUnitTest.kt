package com.example.project_study

import com.example.project_study.data.gall.Answer
import com.example.project_study.data.gall.AnswerRepository
import com.example.project_study.data.gall.GalleryRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AnswerUnitTest(
    @Autowired val galleryRepository: GalleryRepository,
    @Autowired val answerRepository: AnswerRepository
) {
    @Test
    fun create(){
        val answer = Answer(
            content = "Test",
            gallery = galleryRepository.findById(1L).orElseThrow()
        )
        println(answerRepository.save(answer))
    }
}