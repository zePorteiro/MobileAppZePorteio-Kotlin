package com.zeporteiro.zeporteiroapp.data

data class Morador(
    var id: Int? = null,
    var nome: String? = null,
    var email: String? = null,
    var senha: String? = null,
    var cpf: String? = null,
    var cep : String? = null) {

//    fun getNomeApartamento() = "${nome} - ${apartamento}"


}
