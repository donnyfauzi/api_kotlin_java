package com.donnyfauzi.api_kotlin_java.security

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import jakarta.annotation.PostConstruct
import javax.crypto.SecretKey

@Component
class JwtTokenUtil(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.expiration}") private val expiration: Long
) {

    private lateinit var jwtSecret: SecretKey

    @PostConstruct
    fun init() {
        jwtSecret = Keys.hmacShaKeyFor(secret.toByteArray())
    }

    fun generateToken(email: String): String {
        val now = Date()
        val expiryDate = Date(now.time + expiration)

        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(jwtSecret)
            .compact()
    }

    fun getEmailFromJWT(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(jwtSecret)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun validateToken(authToken: String): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(authToken)
            true
        } catch (ex: Exception) {
            false
        }
    }
}
