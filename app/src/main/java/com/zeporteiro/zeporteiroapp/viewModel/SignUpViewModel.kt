//package com.zeporteiro.zeporteiroapp.viewModel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.zeporteiro.zeporteiroapp.data.Morador
//import com.zeporteiro.zeporteiroapp.network.RetroFitService
//import kotlinx.coroutines.launch
//import retrofit2.Response
//
//class SignUpViewModel : ViewModel() {
//    var nome: String = ""
//    var email: String = ""
//    var telefone: String = ""
//    var senha: String = ""
//    var confirmarSenha: String = ""
//
//    fun signUp(onSuccess: () -> Unit, onError: (String) -> Unit) {
//        val newMorador = Morador(nome, email, telefone, senha)
//        viewModelScope.launch {
//            try {
//                val response: Response<Morador> = RetroFitService.getApi().postMorador(newMorador)
//                if (response.isSuccessful) {
//                    onSuccess()
//                } else {
//                    onError("Erro ao registrar: ${response.message()}")
//                }
//            } catch (e: Exception) {
//                onError("Erro ao conectar ao servidor: ${e.message}")
//            }
//        }
//    }
//}