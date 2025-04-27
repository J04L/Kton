package com.example.kton.data.network.models.responses

import com.example.kton.domain.model.UsuarioDomain
import com.example.kton.domain.model.interfaces.Usuario
import com.google.gson.annotations.SerializedName

class UsuarioResponse(

    @SerializedName("nombre")
    override val nombre: String,

    @SerializedName("edad")
    override val edad: Int,

    @SerializedName("email")
    override val email: String,

    @SerializedName("foto")
    override val foto: String,

    @SerializedName("peso")
    override val peso: Int,

    @SerializedName("altura")
    override val altura: Int,

    @SerializedName("objetivo")
    override val objetivo: String,

    @SerializedName("actividadFisica")
    override val actividadFisica: String,

    @SerializedName("sexo")
    override val sexo: String,

    @SerializedName("tiempoDisponibleParaCocinar")
    override val tiempoDisponibleParaCocinar: Int,

    @SerializedName("restriccionesAlimentarias")
    override val restriccionesAlimentarias: List<String>,

    @SerializedName("_id")
    override val id: String,
): Usuario {

    fun toDomain(): UsuarioDomain {
        return UsuarioDomain(
            nombre = nombre,
            edad = edad,
            email = email,
            foto = foto,
            peso = peso,
            altura = altura,
            objetivo = objetivo,
            actividadFisica = actividadFisica,
            sexo = sexo,
            tiempoDisponibleParaCocinar = tiempoDisponibleParaCocinar,
            restriccionesAlimentarias = restriccionesAlimentarias,
            id = id
        )
    }
}