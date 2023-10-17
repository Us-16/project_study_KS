package com.example.project_study

import com.example.project_study.data.account.Account
import com.example.project_study.data.account.AccountRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

@SpringBootTest
class AccountUnitTest(
    @Autowired val accountRepository: AccountRepository,
    @Autowired val passwordEncoder: PasswordEncoder
) {
    @Test
    fun create(){
        val account = Account(
            username = "test",
            password =  passwordEncoder.encode("1234"),
            name = "Joram",
            role = "USER",
            tel="01012341234"
        )

        val result: Any = accountRepository.save(account)
        println("RESULT: $result")
    }

    @Test
    fun readByUsername(){
        val account = accountRepository.findByUsername("Jorim")
        println(account.get())
    }

    @Test
    fun update(){
        val account = accountRepository.findByUsername("Jorim").get()
        account.role = "ADMIN"
        account.modifiedDate = LocalDateTime.now()
        val result = accountRepository.save(account)
        println(result)
    }

    @Test
    fun delete(){
        val result = accountRepository.delete(accountRepository.findByUsername("Jorim").get())
        println(result)
    }
}