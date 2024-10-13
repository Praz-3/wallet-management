package com.crypto.api.auth

import com.crypto.api.util.JwtUtil
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil,
    private val userDetailsService: UserDetailsService
) {

    @PostMapping("/login")
    fun login(@RequestBody authRequest: AuthRequest): Map<String, String> {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password)
        )

        val userDetails = userDetailsService.loadUserByUsername(authRequest.username)
        val token = jwtUtil.generateToken(userDetails)

        return mapOf("token" to token)
    }
}

data class AuthRequest(
    val username: String,
    val password: String
)
