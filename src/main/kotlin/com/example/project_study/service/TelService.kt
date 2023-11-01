package com.example.project_study.service

import com.example.project_study.data.sms.MessageDTO
import com.example.project_study.data.sms.SmsRequestDTO
import com.fasterxml.jackson.databind.ObjectMapper
import net.nurigo.java_sdk.api.Message
import org.apache.tomcat.util.codec.binary.Base64
import org.json.simple.JSONObject
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
    @Value("\${naver-cloud-sms.accessKey}") private val accessKey:String,
    @Value("\${naver-cloud-sms.secretKey}") private val secretKey:String,
    @Value("\${naver-cloud-sms.serviceId}") private val serviceId:String,
    @Value("\${naver-cloud-sms.senderPhone}") private val phone:String,
    @Value("\${spring.sms.cool.key}") private val coolAPIKey:String,
    @Value("\${spring.sms.cool.secret}") private val coolSecret:String,
) {
    fun makeSignature(time: Long):String{
        val message =  StringBuilder()
            .append("POST")
            .append(" ")
            .append("/sms/v2/services${this.serviceId}/messages")
            .append("\n")
            .append(time.toString())
            .append("\n")
            .append(accessKey)
            .toString()
        val signKey = SecretKeySpec(secretKey.toByteArray(Charsets.UTF_8), "HmacSHA256")
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(signKey)
        val rawHmac = mac.doFinal(message.toByteArray(Charsets.UTF_8))
        return Base64.encodeBase64String(rawHmac)
    }

    fun sendSms(messageDTO: MessageDTO): SmsRequestDTO? {
        val time = System.currentTimeMillis()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.set("x-ncp-apigw-timestamp", time.toString())
        headers.set("x-ncp-iam-access-key", accessKey)
        headers.set("x-ncp-apigw-signature-v2", makeSignature(time))

        val messages = ArrayList<MessageDTO>()
        messages.add(messageDTO)

        val request = SmsRequestDTO(
            "SMS", "COMM", "82", phone, messageDTO.content, messages
        )

        val objectMapper = ObjectMapper()
        val body = objectMapper.writeValueAsString(request)
        val httpBody = HttpEntity<String>(body, headers)
        val restTemplate = RestTemplate()
        restTemplate.setRequestFactory(HttpComponentsClientHttpRequestFactory())
        return restTemplate.postForObject(
            URI("https://sens.apigw.ntruss.com/sms/v2/services/$serviceId/messages"),
            httpBody,
            SmsRequestDTO::class.java
        )
    }

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