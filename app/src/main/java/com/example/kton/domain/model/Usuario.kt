package com.example.kton.domain.model

data class Usuario(
    val nombre: String,
    val edad: Int,
    val email: String,
    val foto: String,
    val peso: Int,
    val altura: Int,
    val objetivo: String,
    val actividadFisica: String,
    val sexo: String,
    val tiempoDisponibleParaCocinar: Int,
    val restriccionesAlimentarias: List<String>,
)