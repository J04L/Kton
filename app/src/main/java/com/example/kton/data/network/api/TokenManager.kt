package com.example.kton.data.network.api

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

object TokenManager {
    // Guarda el token
    fun saveToken(context: Context, token: String) {
        val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("auth_token", token).apply()
    }

    // Recupera el token
    fun getToken(context: Context): String? {
        val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        return prefs.getString("auth_token", null)
    }
}