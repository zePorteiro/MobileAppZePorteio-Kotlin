package com.zeporteiro.zeporteiroapp.data

data class CadastroRequest(
    val nome: String,
    val email: String,
    val senha: String,
    val telefone: String,
    val cep: String,
    val numeroApartamento: String
)
