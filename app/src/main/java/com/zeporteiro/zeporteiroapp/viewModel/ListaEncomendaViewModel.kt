package com.zeporteiro.zeporteiroapp.viewModel

import android.util.Log
import androidx.datastore.core.IOException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeporteiro.zeporteiroapp.data.Entrega
import com.zeporteiro.zeporteiroapp.data.datastore.DataStoreManager
import com.zeporteiro.zeporteiroapp.network.ApiZePorteiro
import com.zeporteiro.zeporteiroapp.network.RetroFitService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class ListaEncomendaViewModel(
    private val apiZePorteiro: ApiZePorteiro,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _entregas = MutableStateFlow<List<Entrega>>(emptyList())
    val entregas = _entregas.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

     fun carregarEntregas() {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val token = dataStoreManager.token.first()
                val userId = dataStoreManager.userId.first()

                if (token.isNullOrEmpty()) {
                    _error.value = "Usuário não autenticado"
                    return@launch
                }

                Log.d("ListaEncomendaViewModel", "Iniciando requisição - UserId: $userId")

                val api = RetroFitService.getApi(token)
                val moradorResponse = api.getMoradorById(userId)

                Log.d("ListaEncomendaViewModel", "Resposta do morador - Code: ${moradorResponse.code()}")

                when (moradorResponse.code()) {
                    200 -> {
                        val morador = moradorResponse.body()
                        val numeroApartamento = morador?.apartamento?.numAp

                        if (numeroApartamento != null) {
                            Log.d("ListaEncomendaViewModel", "Buscando entregas para apartamento: $numeroApartamento")
                            val entregasResponse = api.getEntregasPorApartamento(numeroApartamento)

                            if (entregasResponse.isSuccessful) {
                                _entregas.value = entregasResponse.body() ?: emptyList()
                                _error.value = null
                                Log.d("ListaEncomendaViewModel", "Entregas carregadas: ${_entregas.value.size}")
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
                        // Opcional: Redirecionar para login
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
