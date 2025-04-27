package com.example.kton.data.network.models.responses

import com.google.gson.annotations.SerializedName

data class SessionResponse(
    @SerializedName("token")
    val actionToken : String?,
    @SerializedName("usuario")
    val usuario : UsuarioResponse?,
)