package com.example.kton.domain.repository

import com.example.kton.domain.model.UsuarioDomain

interface SessionRepository {
    suspend fun userSession(token: String): Result<UsuarioDomain>
}