package com.example.project_study.data.sms

data class SmsRequestDTO(
    val type:String,
    val contentType:String,
    val countryType:String,
    val from: String,
    val content: String,
    val messages:List<MessageDTO>
)