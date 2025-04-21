package com.donnyfauzi.api_kotlin_java.exception

import com.donnyfauzi.api_kotlin_java.dto.AuthResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BaseException::class)
    fun handleBaseException(ex: BaseException): ResponseEntity<Map<String, String>> {
        val body = mapOf("message" to (ex.message ?: "Something went wrong"))
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    fun handleGeneralException(ex: Exception): ResponseEntity<Map<String, String>> {
        val body = mapOf("message" to "Internal Server Error")
        return ResponseEntity(body, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
