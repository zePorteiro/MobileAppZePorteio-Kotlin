package com.zeporteiro.zeporteiroapp.data

data class LoginResponse(
    val userId: Int,
    val nome: String,
    val email: String,
    val token: String?
)
