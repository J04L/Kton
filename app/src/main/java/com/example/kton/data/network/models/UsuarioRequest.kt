package com.example.kton.data.network.models

import com.example.kton.domain.model.Usuario
import com.google.gson.annotations.SerializedName


data class UsuarioRequest(
    @SerializedName("usuarioReq")
    val usuario: Usuario,

    @SerializedName("password")
    val password: String
)