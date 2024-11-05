package com.zeporteiro.zeporteiroapp.data

data class Entrega (
    var id: Int? = null,
    var morador: Morador? = null,
    var dataEntrega: String? = null,
    var dataRetirada: String? = null
) {

}
