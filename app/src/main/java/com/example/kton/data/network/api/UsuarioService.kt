package com.example.kton.data.network.api

import com.example.kton.data.network.models.responses.UsuarioResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UsuarioService {
    @GET ("usuarios/{id}") suspend fun getUsuario(
        @Path ("id") id: String
    ): Response<UsuarioResponse>
}