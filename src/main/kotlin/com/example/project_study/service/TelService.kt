package com.example.project_study.service

import com.example.project_study.data.sms.MessageDTO
import com.example.project_study.data.sms.SmsRequestDTO
import com.fasterxml.jackson.databind.ObjectMapper
import net.nurigo.java_sdk.api.Message
import org.apache.tomcat.util.codec.binary.Base64
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.lang.StringBuilder
import java.net.URI
import java.security.SecureRandom
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Service
class TelService(
    @Value("\${naver-cloud-sms.senderPhone}") private val phone:String,
    @Value("\${spring.sms.cool.key}") private val coolAPIKey:String,
    @Value("\${spring.sms.cool.secret}") private val coolSecret:String,
) {
    fun createCode(): String{
        val secureRandom: SecureRandom = SecureRandom()
        return (1..6).joinToString(separator = "") {
            secureRandom.nextInt(9).toString()
        }
    }

    fun sendSmsCoolSMS(dto: MessageDTO): String {
        val coolSms = Message(coolAPIKey, coolSecret)

        val params = HashMap<String, String>()
        params["to"] = dto.to
        params["from"] = phone
        params["type"] = "SMS"
        params["text"] = "인증번호: ${dto.content}"
        params["app_version"] = "test app 1.2"

        runCatching {
            val obj = coolSms.send(params)
            println(obj.toString())
        }
        return dto.content
    }
}