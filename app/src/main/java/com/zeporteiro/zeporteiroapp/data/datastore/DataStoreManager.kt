package com.zeporteiro.zeporteiroapp.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class DataStoreManager(private val context: Context) {

    // Funções para salvar dados
    suspend fun saveUserId(userId: Int) {
        context.dataStore.edit { preferences ->
            preferences[UserPreferences.USER_ID] = userId
        }
    }

    suspend fun saveEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[UserPreferences.EMAIL] = email
        }
    }

    suspend fun saveNome(nome: String) {
        context.dataStore.edit { preferences ->
            preferences[UserPreferences.NOME] = nome
        }
    }

    suspend fun saveToken(token: String?) {
        context.dataStore.edit { preferences ->
            if (token != null) {
                preferences[UserPreferences.TOKEN] = token
            } else {
                preferences.remove(UserPreferences.TOKEN)
            }
        }
    }

    // Funções para obter dados como Flow
    val userId: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[UserPreferences.USER_ID] ?: 0
        }

    val email: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[UserPreferences.EMAIL] ?: ""
        }

    val nome: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[UserPreferences.NOME] ?: ""
        }

    val token: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[UserPreferences.TOKEN]
        }

    // Função para limpar todos os dados
    suspend fun clearData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    // Função auxiliar para obter token de forma síncrona quando necessário
    suspend fun getTokenBlocking(): String? = token.collect { it }.toString()
}