package com.zeporteiro.zeporteiroapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeporteiro.zeporteiroapp.data.CadastroRequest
import com.zeporteiro.zeporteiroapp.data.Morador
import com.zeporteiro.zeporteiroapp.network.ApiZePorteiro
import com.zeporteiro.zeporteiroapp.network.RetroFitService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class SignUpViewModel(
    private val api: ApiZePorteiro
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _cadastroSuccess = MutableStateFlow(false)
    val cadastroSuccess = _cadastroSuccess.asStateFlow()

    // Dados da primeira tela
    private val _nome = MutableStateFlow("")
    val nome = _nome.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _telefone = MutableStateFlow("")
    val telefone = _telefone.asStateFlow()

    private val _senha = MutableStateFlow("")
    val senha = _senha.asStateFlow()

    // Dados da segunda tela
    private val _cep = MutableStateFlow("")
    val cep = _cep.asStateFlow()

    private val _numeroApartamento = MutableStateFlow("")
    val numeroApartamento = _numeroApartamento.asStateFlow()

    private val _logradouro = MutableStateFlow("")
    val logradouro = _logradouro.asStateFlow()

    private val _numero = MutableStateFlow("")
    val numero = _numero.asStateFlow()

    private val _bloco = MutableStateFlow("")
    val bloco = _bloco.asStateFlow()

    private val _nomeCondominio = MutableStateFlow("")
    val nomeCondominio = _nomeCondominio.asStateFlow()

    private fun validarDadosPessoais(
        nome: String,
        email: String,
        senha: String,
        telefone: String
    ): Boolean {
        if (nome.isEmpty()) {
            _error.value = "Nome não pode estar vazio"
            return false
        }

        if (!email.contains("@")) {
            _error.value = "E-mail inválido"
            return false
        }

        if (senha.length < 6) {
            _error.value = "Senha deve ter no mínimo 6 caracteres"
            return false
        }

        if (telefone.isEmpty()) {
            _error.value = "Telefone não pode estar vazio"
            return false
        }

        return true
    }

    fun setDadosPessoais(
        nome: String,
        email: String,
        telefone: String,
        senha: String,
        confirmarSenha: String
    ): Boolean {
        if (senha != confirmarSenha) {
            _error.value = "As senhas não conferem"
            return false
        }

        if (!validarDadosPessoais(nome.trim(), email.trim(), senha, telefone.trim())) {
            return false
        }

        _nome.value = nome.trim()
        _email.value = email.trim()
        _telefone.value = telefone.trim()
        _senha.value = senha

        Log.d("SignUpViewModel", """
           Dados pessoais salvos:
           Nome: ${_nome.value}
           Email: ${_email.value}
           Telefone: ${_telefone.value}
           Senha definida: ${_senha.value.isNotEmpty()}
       """.trimIndent())

        return true
    }

    private fun validarDadosEndereco(cep: String, numeroApartamento: String): Boolean {
        if (cep.isEmpty() || cep.length != 8) {
            _error.value = "CEP inválido"
            return false
        }

        if (numeroApartamento.isEmpty()) {
            _error.value = "Número do apartamento é obrigatório"
            return false
        }

        return true
    }

    fun setDadosEndereco(
        cep: String,
        logradouro: String,
        numero: String,
        numeroApartamento: String,
        bloco: String,
        nomeCondominio: String
    ): Boolean {
        val cepLimpo = cep.replace("-", "").trim()

        if (!validarDadosEndereco(cepLimpo, numeroApartamento.trim())) {
            return false
        }

        _cep.value = cepLimpo
        _logradouro.value = logradouro.trim()
        _numero.value = numero.trim()
        _numeroApartamento.value = numeroApartamento.trim()
        _bloco.value = bloco.trim()
        _nomeCondominio.value = nomeCondominio.trim()

        Log.d("SignUpViewModel", """
           Dados de endereço salvos:
           CEP: ${_cep.value}
           Logradouro: ${_logradouro.value}
           Número: ${_numero.value}
           Número Apartamento: ${_numeroApartamento.value}
           Bloco: ${_bloco.value}
           Nome Condomínio: ${_nomeCondominio.value}
       """.trimIndent())

        return true
    }

    fun cadastrar() {
        // Validação inicial dos dados pessoais
        if (!validarDadosPessoais(_nome.value, _email.value, _senha.value, _telefone.value)) {
            _error.value = "Dados pessoais inválidos"
            return
        }

        // Validação dos dados de endereço
        if (!validarDadosEndereco(_cep.value, _numeroApartamento.value)) {
            _error.value = "Dados de endereço inválidos"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                Log.d("SignUpViewModel", "Iniciando processo de cadastro")

                val cadastroRequest = CadastroRequest(
                    nome = _nome.value,
                    email = _email.value,
                    senha = _senha.value,
                    telefone = _telefone.value,
                    cep = _cep.value,
                    numeroApartamento = _numeroApartamento.value
                )

                Log.d("SignUpViewModel", """
                   Dados do request:
                   Nome: ${cadastroRequest.nome}
                   Email: ${cadastroRequest.email}
                   Telefone: ${cadastroRequest.telefone}
                   CEP: ${cadastroRequest.cep}
                   Número Apartamento: ${cadastroRequest.numeroApartamento}
               """.trimIndent())

                val responseApartamento = api.verificarApartamento(
                    cep = cadastroRequest.cep,
                    numeroApartamento = cadastroRequest.numeroApartamento
                )
                Log.d("SignUpViewModel", "Resposta verificação apartamento: ${responseApartamento.code()}")

                if (!responseApartamento.isSuccessful) {
                    _error.value = "Apartamento não encontrado neste condomínio"
                    return@launch
                }

                val response = api.cadastrarMorador(cadastroRequest)
                Log.d("SignUpViewModel", "Resposta cadastro morador: ${response.code()}")

                if (response.isSuccessful) {
                    Log.d("SignUpViewModel", "Cadastro realizado com sucesso")
                    _cadastroSuccess.value = true
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("SignUpViewModel", "Erro no cadastro: $errorBody")
                    _error.value = when (response.code()) {
                        400 -> "Dados inválidos: verifique se todos os campos foram preenchidos corretamente"
                        409 -> "E-mail já cadastrado"
                        404 -> "CEP ou apartamento não encontrado"
                        else -> "Erro ao cadastrar: ${response.message()}"
                    }
                }
            } catch (e: Exception) {
                Log.e("SignUpViewModel", "Erro no cadastro", e)
                _error.value = "Erro ao cadastrar: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
