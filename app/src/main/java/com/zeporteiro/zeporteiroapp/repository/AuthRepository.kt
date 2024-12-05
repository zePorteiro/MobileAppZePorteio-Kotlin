package com.zeporteiro.zeporteiroapp.repository

import android.util.Log
import com.zeporteiro.zeporteiroapp.data.LoginRequest
import com.zeporteiro.zeporteiroapp.data.LoginResponse
import com.zeporteiro.zeporteiroapp.data.datastore.DataStoreManager
import com.zeporteiro.zeporteiroapp.di.SessaoUsuario
import com.zeporteiro.zeporteiroapp.network.ApiZePorteiro

class AuthRepository(
    private val apiZePorteiro: ApiZePorteiro,
    private val sessaoUsuario: SessaoUsuario,
    private val dataStoreManager: DataStoreManager
) {
    suspend fun login(email: String, password: String): Boolean {
        return try {
            Log.d("AuthRepository", "Iniciando login com email: $email")
            val loginRequest = LoginRequest(email, password)
            val response = apiZePorteiro.login(loginRequest)

            Log.d("AuthRepository", "Resposta recebida: ${response.code()}")

            if (response.isSuccessful) {
                val loginResponse = response.body()
                if (loginResponse != null) {
                    Log.d("AuthRepository", """
                        Login bem sucedido:
                        UserId: ${loginResponse.userId}
                        Nome: ${loginResponse.nome}
                        Email: ${loginResponse.email}
                        Token: ${loginResponse.token != null}
                    """.trimIndent())

                    // Salvar no DataStore
                    dataStoreManager.saveToken(loginResponse.token)
                    dataStoreManager.saveUserId(loginResponse.userId)
                    dataStoreManager.saveEmail(loginResponse.email)
                    dataStoreManager.saveNome(loginResponse.nome)

                    Log.d("AuthRepository", "Dados salvos no DataStore")
                    true
                } else {
                    Log.e("AuthRepository", "Resposta nula do servidor")
                    false
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("AuthRepository", "Falha no login: ${response.code()} - $errorBody")
                false
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Exceção no login", e)
            false
        }
    }
}
