package com.example.kton.domain.model

// domain/model/Receta.kt
data class Receta(
    val id: String?,
    val titulo: String,
    val descripcion: String,
    val ingredientes: List<Ingrediente>,
    val informacionNutricional: InformacionNutricional,
    val herramientas: List<String>,
    val tiempoTotal: Int,
    val resultados: List<Resultado>
)

// domain/model/Ingrediente.kt
data class Ingrediente(
    val nombre: String,
    val cantidad: String?,
    val peso: Int?,
    val porcentajeEnergetico: PorcentajeEnergetico?
)

// domain/model/PorcentajeEnergetico.kt
data class PorcentajeEnergetico(
    val energia: Int,
    val proteinas: Int,
    val carbohidratos: Int,
    val grasas: Int
)

// domain/model/InformacionNutricional.kt
data class InformacionNutricional(
    val energia: Int,
    val proteinas: Int,
    val carbohidratos: Int,
    val grasas: Int
)

// domain/model/Resultado.kt
data class Resultado(
    val nombre: String,
    val pasos: List<Paso>
)

// domain/model/Paso.kt
data class Paso(
    val verbo: String,
    val ingredientes: List<IngredientePaso>,
    val herramienta: String?,
    val frase: String,
    val tiempo: Int?
)

// domain/model/IngredientePaso.kt
data class IngredientePaso(
    val nombre: String,
    val cantidad: String?,
    val peso: Int?
)