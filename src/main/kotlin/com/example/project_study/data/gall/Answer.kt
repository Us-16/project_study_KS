package com.example.project_study.data.gall

import com.example.project_study.data.account.Account
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name="ANSWER")
data class Answer(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long? = null,
    @Column(nullable = false)
    var content: String,
    @Column(nullable = false)
    val createdDate: LocalDateTime? = LocalDateTime.now(),
    @Column(nullable = true)
    var modifiedDate: LocalDateTime? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="gallery_id", nullable = false)
    var gallery: Gallery,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_id", nullable = false)
    var account:Account
)
