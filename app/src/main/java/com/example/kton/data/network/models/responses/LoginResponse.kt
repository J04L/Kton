package com.example.kton.data.network.models.responses

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token")
    val token : String?,
    @SerializedName("action_token")
    val actionToken : String?,
    @SerializedName("usuario")
    val usuario : UsuarioResponse?,
)