package com.example.kton.domain.model

import com.example.kton.domain.model.interfaces.Usuario

data class UsuarioDomain(
    override val id: String,
    override val nombre: String,
    override val email: String,
    override val foto: String,
    override val peso: Int?,
    override val altura: Int?,
    override val edad: Int?,
    override val objetivo: String?,
    override val actividadFisica: String?,
    override val sexo: String?,
    override val tiempoDisponibleParaCocinar: Int?,
    override val restriccionesAlimentarias: List<String>?,
) : Usuario
