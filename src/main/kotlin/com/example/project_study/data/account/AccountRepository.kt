package com.example.project_study.data.account

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface AccountRepository:JpaRepository<Account, Long> {
    fun findByUsername(username:String): Optional<Account>
}