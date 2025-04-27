package com.example.kton.domain.model.interfaces

interface Usuario {
    val id: String
    val nombre: String
    val email: String
    val foto: String
    val edad: Int?
    val peso: Int?
    val altura: Int?
    val objetivo: String?
    val actividadFisica: String?
    val sexo: String?
    val tiempoDisponibleParaCocinar: Int?
    val restriccionesAlimentarias: List<String>?
}