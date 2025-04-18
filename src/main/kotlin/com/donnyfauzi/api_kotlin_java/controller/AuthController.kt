package com.donnyfauzi.api_kotlin_java.controller

import com.donnyfauzi.api_kotlin_java.dto.AuthRequest
import com.donnyfauzi.api_kotlin_java.dto.AuthResponse
import com.donnyfauzi.api_kotlin_java.dto.RegisterRequest
import com.donnyfauzi.api_kotlin_java.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(authService.register(request))
    }

    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequest): ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(authService.login(request))
    }
}
