package com.donnyfauzi.api_kotlin_java.repository

import com.donnyfauzi.api_kotlin_java.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): Optional<User>
}