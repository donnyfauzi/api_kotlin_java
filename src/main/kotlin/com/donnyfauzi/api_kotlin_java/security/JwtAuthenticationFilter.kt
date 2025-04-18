package com.donnyfauzi.api_kotlin_java.security

import com.donnyfauzi.api_kotlin_java.service.AuthService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtTokenUtil: JwtTokenUtil,
    private val authService: AuthService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader("Authorization")
        logger.info("Authorization Header: $header")
        if (header != null && header.startsWith("Bearer ")) {
            val token = header.substring(7)

            try {
                if (jwtTokenUtil.validateToken(token)) {
                    val email = jwtTokenUtil.getEmailFromJWT(token)

                    // Hanya set auth kalau belum ada
                    if (SecurityContextHolder.getContext().authentication == null) {
                        val userDetails = authService.loadUserByUsername(email)

                        val authentication = UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.authorities
                        )

                        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                        SecurityContextHolder.getContext().authentication = authentication
                    }
                }
            } catch (ex: Exception) {
                println("Gagal set authentication dari token: ${ex.message}")
                // Bisa tambahkan logging lebih detail di sini
            }
        }

        filterChain.doFilter(request, response)
    }
}
