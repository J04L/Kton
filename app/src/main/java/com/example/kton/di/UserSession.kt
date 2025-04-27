package com.example.kton.di

import android.content.Context
import com.example.kton.domain.model.UsuarioDomain
import com.example.kton.domain.model.interfaces.Usuario
import com.example.kton.utils.SecureTokenStorage
import dagger.hilt.android.qualifiers.ApplicationContext

object UserSession {
    var user : UsuarioDomain? = null
    var action_token : String? = null

    fun saveUser(usuario: UsuarioDomain, token: String){
        action_token = token
        user = usuario
    }

}