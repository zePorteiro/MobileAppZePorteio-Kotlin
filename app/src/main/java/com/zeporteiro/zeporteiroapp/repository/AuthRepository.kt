package com.zeporteiro.zeporteiroapp.repository

import com.zeporteiro.zeporteiroapp.data.LoginRequest
import com.zeporteiro.zeporteiroapp.data.LoginResponse
import com.zeporteiro.zeporteiroapp.di.SessaoUsuario
import com.zeporteiro.zeporteiroapp.network.ApiZePorteiro

class AuthRepository(
    private val apiZePorteiro: ApiZePorteiro,
    private val sessaoUsuario: SessaoUsuario
) {
    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            val loginRequest = LoginRequest(email, password)
            val response = apiZePorteiro.login(loginRequest)
            if (response.isSuccessful) {
                val loginResponse = response.body()
                loginResponse?.let {
                    sessaoUsuario.token = it.token
                }
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
