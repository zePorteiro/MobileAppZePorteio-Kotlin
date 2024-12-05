package com.zeporteiro.zeporteiroapp.data.datastore

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object UserPreferences {
    val USER_ID = intPreferencesKey("user_id")
    val EMAIL = stringPreferencesKey("email")
    val NOME = stringPreferencesKey("nome")
    val TOKEN = stringPreferencesKey("token")
}