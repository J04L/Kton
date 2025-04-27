package com.example.kton.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first


val Context.dataStore by preferencesDataStore(name = "secure_prefs")

object SecureTokenStorage {
    private val TOKEN_KEY = stringPreferencesKey("auth_token")

    suspend fun saveToken(context: Context, token: String) {
        val secretKey = CryptoKeyManager.getOrCreateSecretKey()
        val encryptedToken = AESUtil.encrypt(token, secretKey)

        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = encryptedToken
        }
    }

    suspend fun getToken(context: Context): String? {
        val secretKey = CryptoKeyManager.getOrCreateSecretKey()
        val prefs = context.dataStore.data.first()
        val encryptedToken = prefs[TOKEN_KEY] ?: return null

        return AESUtil.decrypt(encryptedToken, secretKey)
    }

}
