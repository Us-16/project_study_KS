package com.example.project_study.data.account

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "ACCOUNT")
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long? = null,
    @Column(nullable = false, length = 32, unique = true)
    var username:String,
    @Column(nullable = false, length = 64)
    var password:String,
    @Column(nullable = false, length=30)
    var name:String,
    @Column(nullable =  false, unique = true)
    var tel:String,
    @Column(nullable = false)
    var role:String,
    @Column(nullable = false)
    var createDate:LocalDateTime = LocalDateTime.now(),
    @Column(nullable = true)
    var modifiedDate:LocalDateTime? = null
)