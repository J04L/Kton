package com.example.kton.data.network.models.responses

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("usuario")
    val usuario: UsuarioResponse? = null,
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("action_token")
    val actionToken : String?
)