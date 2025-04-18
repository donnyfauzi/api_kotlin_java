package com.donnyfauzi.api_kotlin_java.dto

data class AuthResponse(
    val message: String? = null,
    val token: String? = null,
    val user: Map<String, String>? = null  // Menyimpan data pengguna (misalnya name dan email)
)