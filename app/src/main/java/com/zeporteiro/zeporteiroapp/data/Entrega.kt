package com.zeporteiro.zeporteiroapp.data

data class Entrega (
    val id: Int? = null,
    val tipoEntrega: String? = null,
    val dataRecebimentoPorteiro: String? = null,
    val dataRecebimentoMorador: String? = null,
    val recebido: Boolean = false,
    val porteiro: Porteiro? = null,
    val apartamento: Apartamento? = null
) {

}
