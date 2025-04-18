package com.donnyfauzi.api_kotlin_java.service

import com.donnyfauzi.api_kotlin_java.dto.AuthRequest
import com.donnyfauzi.api_kotlin_java.dto.AuthResponse
import com.donnyfauzi.api_kotlin_java.dto.RegisterRequest
import com.donnyfauzi.api_kotlin_java.entity.User
import com.donnyfauzi.api_kotlin_java.repository.UserRepository
import com.donnyfauzi.api_kotlin_java.security.JwtTokenUtil
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenUtil: JwtTokenUtil
) : UserDetailsService {

    fun register(request: RegisterRequest): AuthResponse {
        // Cek apakah email sudah digunakan
        val existingUser = userRepository.findByEmail(request.email)
        if (existingUser.isPresent) {
            val responseMessage = "Email already in use"
            return AuthResponse(message = responseMessage)
        }

        val user = User(
            name = request.name,
            email = request.email,
            password = passwordEncoder.encode(request.password)
        )

        userRepository.save(user)

        val responseMessage = "Registration successful"
        val userData = mapOf("name" to user.name, "email" to user.email, "password" to user.password)

        // Kembalikan response dengan pesan dan data user
        return AuthResponse(message = responseMessage, user = userData)
    }

    fun login(request: AuthRequest): AuthResponse {
        val user = userRepository.findByEmail(request.email)
            .orElseThrow { UsernameNotFoundException("Email not found") }

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("Invalid password")
        }

        val token = jwtTokenUtil.generateToken(user.email)
        return AuthResponse(token)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username)
            .orElseThrow { UsernameNotFoundException("Email not found") }

        return org.springframework.security.core.userdetails.User(
            user.email, user.password, emptyList()
        )
    }
}
