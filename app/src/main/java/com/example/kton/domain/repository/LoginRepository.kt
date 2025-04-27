package com.example.kton.domain.repository

import com.example.kton.domain.model.UsuarioDomain

interface LoginRepository {
    suspend fun login(username:String, pass:String): Result<UsuarioDomain>

}