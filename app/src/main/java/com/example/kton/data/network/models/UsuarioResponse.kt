package com.example.kton.data.network.models

import com.example.kton.domain.model.Usuario
import com.example.kton.utils.usuarioPruebas
import com.google.gson.annotations.SerializedName

class UsuarioResponse(
    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("edad")
    val edad: Int,

    @SerializedName("email")
    val email: String,

    @SerializedName("foto")
    val foto: String,

    @SerializedName("peso")
    val peso: Int,

    @SerializedName("altura")
    val altura: Int,

    @SerializedName("objetivo")
    val objetivo: String,

    @SerializedName("actividadFisica")
    val actividadFisica: String,

    @SerializedName("sexo")
    val sexo: String,

    @SerializedName("tiempoDisponibleParaCocinar")
    val tiempoDisponibleParaCocinar: Int,

    @SerializedName("restriccionesAlimentarias")
    val restriccionesAlimentarias: List<String>,
) {

    fun toDomain() : Usuario{
        return usuarioPruebas
    }
}