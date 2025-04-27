package com.example.kton.domain.repository

import com.example.kton.domain.model.UsuarioDomain

interface UsuarioRepository {
    suspend fun getUsuario(id: String): Result<UsuarioDomain>
    /*suspend fun createUsuario(usuario: UsuarioDomain, password: String): Result<UsuarioDomain>*/
}