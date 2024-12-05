package com.zeporteiro.zeporteiroapp.data

data class Morador(
    val id: Int,
    val nome: String,
    val email: String,
    val apartamento: Apartamento) {

//    fun getNomeApartamento() = "${nome} - ${apartamento}"


}
