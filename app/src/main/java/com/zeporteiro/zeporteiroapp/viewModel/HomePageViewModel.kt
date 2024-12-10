package com.zeporteiro.zeporteiroapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeporteiro.zeporteiroapp.data.datastore.DataStoreManager
import com.zeporteiro.zeporteiroapp.network.ApiZePorteiro
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomePageViewModel(
    private val dataStoreManager: DataStoreManager,
    private val apiZe: ApiZePorteiro
): ViewModel() {
    private val _estado = MutableStateFlow(EstadoTelaInicial())
    val estado = _estado.asStateFlow()

    init {
        carregarDados()
    }

    private fun carregarDados() {
        viewModelScope.launch {
            try {
                _estado.value = _estado.value.copy(carregando = true)

                // Coletar o nome do DataStore
                dataStoreManager.nome.collect { nome ->
                    _estado.value = _estado.value.copy(
                        nomeMorador = nome,
                        carregando = false
                    )
                }

                // Também podemos usar o userId para buscar as entregas
                dataStoreManager.userId.collect { userId ->
                    if (userId != 0) {
                        carregarEntregas(userId)
                    }
                }
            } catch (e: Exception) {
                _estado.value = _estado.value.copy(
                    erro = "Erro ao carregar dados do morador",
                    carregando = false
                )
            }
        }
    }

    private fun carregarEntregas(userId: Int) {
        viewModelScope.launch {
            try {
                val response = apiZe.getMoradorById(userId)
                if (response.isSuccessful) {
                    val morador = response.body()
                    morador?.apartamento?.numAp?.let { numAp ->
                        val entregasResponse = apiZe.getEntregasPorApartamento(numAp)
                        if (entregasResponse.isSuccessful) {
                            val entregas = entregasResponse.body() ?: emptyList()
                            val entregasPendentes = entregas.count { !it.recebido }
                            _estado.value = _estado.value.copy(
                                entregasPendentes = entregasPendentes,
                                carregando = false
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _estado.value = _estado.value.copy(
                    erro = "Erro ao carregar entregas",
                    carregando = false
                )
            }
        }
    }

    data class EstadoTelaInicial(
        val nomeMorador: String = "",
        val entregasPendentes: Int = 0,
        val carregando: Boolean = false,
        val erro: String? = null
    )
}