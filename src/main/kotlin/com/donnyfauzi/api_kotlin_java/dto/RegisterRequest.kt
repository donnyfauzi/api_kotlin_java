package com.donnyfauzi.api_kotlin_java.dto

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)