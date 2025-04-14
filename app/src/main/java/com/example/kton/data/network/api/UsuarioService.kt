package com.example.kton.data.network.api

import com.example.kton.data.network.models.UsuarioRequest
import com.example.kton.data.network.models.UsuarioResponse
import com.example.kton.domain.model.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UsuarioService {
    @GET ("usuarios/{id}") suspend fun getUsuario(
        @Path ("id") id: String
    ): Response<UsuarioResponse>
    @POST ("usuarios") suspend fun postUsuario(
        @Body usuario: UsuarioRequest
    ): Response<UsuarioResponse>
}