package com.example.project_study.data.gall

import com.example.project_study.data.account.Account
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.ToString
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="GALLERY")
data class Gallery(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_id", nullable = false)
    var account:Account,
    @Column(nullable = false)
    var title:String,
    @Column(nullable = false)
    var content:String,
    @Column(nullable = false)
    var createdDate:LocalDateTime = LocalDateTime.now(),
    @Column(nullable = true)
    var modifiedDate:LocalDateTime? = null
)
