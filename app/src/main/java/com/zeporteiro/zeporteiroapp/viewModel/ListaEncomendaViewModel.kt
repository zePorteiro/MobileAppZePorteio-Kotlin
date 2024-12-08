package com.zeporteiro.zeporteiroapp.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.datastore.core.IOException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeporteiro.zeporteiroapp.data.Entrega
import com.zeporteiro.zeporteiroapp.data.datastore.DataStoreManager
import com.zeporteiro.zeporteiroapp.network.ApiZePorteiro
import com.zeporteiro.zeporteiroapp.network.RetroFitService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ListaEncomendaViewModel(
    private val apiZePorteiro: ApiZePorteiro,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    val _entregas = mutableStateListOf<Entrega>()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

//    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

    fun carregarEntregas() {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val token = dataStoreManager.token.first()
                val userId = dataStoreManager.userId.first()

                Log.d("ListaEncomendaViewModel", "Token: $token, UserId: $userId")

                if (token.isNullOrEmpty()) {
                    _error.value = "Usuário não autenticado"
                    return@launch
                }

                val api = RetroFitService.getApi(token)
                val moradorResponse = api.getMoradorById(userId)

                Log.d("ListaEncomendaViewModel", "Resposta morador: ${moradorResponse.body()}")

                when (moradorResponse.code()) {
                    200 -> {
                        val morador = moradorResponse.body()
                        val numeroApartamento = morador?.apartamento?.numAp

                        if (numeroApartamento != null) {
                            Log.d("ListaEncomendaViewModel", "Buscando entregas para apartamento: $numeroApartamento")
                            val entregasResponse = api.getEntregasPorApartamento(numeroApartamento)

                            Log.d("ListaEncomendaViewModel", "Resposta entregas: ${entregasResponse.body()}")

                            if (entregasResponse.isSuccessful) {
                                _entregas.clear()
                                _entregas.addAll( entregasResponse.body() ?: emptyList())
                                _error.value = null
                                Log.d("ListaEncomendaViewModel", "Estado atualizado: ${_entregas}")
                            } else {
                                _error.value = "Erro ao carregar entregas: ${entregasResponse.message()}"
                                Log.e("ListaEncomendaViewModel", "Erro na API de entregas: ${entregasResponse.code()}")
                            }
                        } else {
                            _error.value = "Apartamento não encontrado para este morador"
                        }
                    }
                    401 -> {
                        _error.value = "Sessão expirada. Por favor, faça login novamente"
                    }
                    else -> {
                        _error.value = "Erro ao buscar informações do morador (${moradorResponse.code()})"
                    }
                }
            } catch (e: Exception) {
                _error.value = when (e) {
                    is IOException -> "Erro de conexão. Verifique sua internet."
                    else -> "Erro ao carregar entregas: ${e.message}"
                }
                Log.e("ListaEncomendaViewModel", "Exceção", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun confirmarRecebimento(entregaId: Int) {
        viewModelScope.launch {
            try {
                val token = dataStoreManager.token.first()
                if (!token.isNullOrEmpty()) {
                    val api = RetroFitService.getApi(token)
                    val response = api.registrarEntregaRecebida(entregaId)
                    if (response.isSuccessful) {
                        carregarEntregas()
                    } else {
                        _error.value = "Erro ao confirmar recebimento"
                    }
                }
            } catch (e: Exception) {
                _error.value = "Erro ao confirmar recebimento: ${e.message}"
            }
        }
    }
}
