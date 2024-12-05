package com.zeporteiro.zeporteiroapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeporteiro.zeporteiroapp.data.datastore.DataStoreManager
import com.zeporteiro.zeporteiroapp.network.ApiZePorteiro
import com.zeporteiro.zeporteiroapp.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginPageViewModel(
    private val authRepository: AuthRepository,
    private val dataStoreManager: DataStoreManager,
    private val apiZe: ApiZePorteiro
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError = _loginError.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    // Observar o token para determinar o estado do login
    val isLoggedIn = dataStoreManager.token
        .map { token ->
            Log.d("LoginViewModel", "Token atualizado: ${token != null}")
            token != null
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        _loginError.value = null
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        _loginError.value = null
    }

    fun login() {
        viewModelScope.launch {
            _isLoading.value = true
            _loginError.value = null

            try {
                Log.d("LoginPageViewModel", "Iniciando login com email: ${_email.value}")

                when {
                    _email.value.isEmpty() -> {
                        _loginError.value = "Digite seu email"
                        return@launch
                    }
                    _password.value.isEmpty() -> {
                        _loginError.value = "Digite sua senha"
                        return@launch
                    }
                    !_email.value.contains("@") -> {
                        _loginError.value = "Email inválido"
                        return@launch
                    }
                }

                val success = authRepository.login(_email.value.trim(), _password.value)

                if (success) {
                    Log.d("LoginPageViewModel", "Login bem sucedido, redirecionando...")
                } else {
                    Log.d("LoginPageViewModel", "Login falhou")
                    _loginError.value = "Email ou senha incorretos"
                }

            } catch (e: Exception) {
                Log.e("LoginPageViewModel", "Erro no login", e)
                _loginError.value = "Erro ao fazer login: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                dataStoreManager.clearData()
                _email.value = ""
                _password.value = ""
            } catch (e: Exception) {
                Log.e("LoginPageViewModel", "Erro ao fazer logout", e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Limpar recursos se necessário
    }
}
