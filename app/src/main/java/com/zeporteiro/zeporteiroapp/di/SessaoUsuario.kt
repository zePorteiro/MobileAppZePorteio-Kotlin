package com.zeporteiro.zeporteiroapp.di

import com.zeporteiro.zeporteiroapp.data.datastore.DataStoreManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

data class SessaoUsuario(
    private val dataStoreManager: DataStoreManager
) {
    var userId: Int
        get() = runBlocking { dataStoreManager.userId.first() }
        set(value) = runBlocking { dataStoreManager.saveUserId(value) }

    var email: String
        get() = runBlocking { dataStoreManager.email.first() }
        set(value) = runBlocking { dataStoreManager.saveEmail(value) }

    var nome: String
        get() = runBlocking { dataStoreManager.nome.first() }
        set(value) = runBlocking { dataStoreManager.saveNome(value) }

    var token: String?
        get() = runBlocking { dataStoreManager.token.first() }
        set(value) = runBlocking { dataStoreManager.saveToken(value) }

    fun clearSession() = runBlocking {
        dataStoreManager.clearData()
    }
}
