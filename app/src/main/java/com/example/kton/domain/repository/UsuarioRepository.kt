package com.example.kton.domain.repository

import com.example.kton.domain.model.Usuario

interface UsuarioRepository {
    suspend fun getUsuario(id: String): Result<Usuario>
    suspend fun createUsuario(usuario: Usuario, password: String): Result<Usuario>
}