package com.zeporteiro.zeporteiroapp.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {

    private val _estado = MutableStateFlow(EstadoPerfil(
        nomeMorador = "Jhulia",
        email = "jhulia.silva@email.com",
        telefoneCelular = "(11) 96926-9404",
        numeroApartamento = "1"
    ))
    val estado = _estado.asStateFlow()

    fun atualizarCampo(campo: Campo, novoValor: String) {
        _estado.value = when (campo) {
            Campo.NOME -> _estado.value.copy(nomeMorador = novoValor)
            Campo.EMAIL -> _estado.value.copy(email = novoValor)
            Campo.CELULAR -> _estado.value.copy(telefoneCelular = novoValor)
            Campo.APARTAMENTO -> _estado.value.copy(numeroApartamento = novoValor)
        }
    }

    enum class Campo {
        NOME, EMAIL, CELULAR, APARTAMENTO
    }

    data class EstadoPerfil(
        val nomeMorador: String,
        val email: String,
        val telefoneCelular: String,
        val numeroApartamento: String
    )
}