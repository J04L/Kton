package com.example.kton.domain.model

// domain/model/RecetaDomain.kt
data class RecetaDomain(
    override val id: String?,
    override val titulo: String,
    override val descripcion: String,
    override val ingredientes: List<IngredienteDomain>,
    override val informacionNutricional: InformacionNutricionalDomain,
    override val herramientas: List<String>,
    override val tiempo: Int,
    override val resultados: List<ResultadoDomain>,
    override val restriccionesAlimentarias: List<String>,
    override val etiquetas: List<String>
): Receta

// domain/model/Ingrediente.kt
data class IngredienteDomain(
    override val nombre: String,
    override val cantidad: String,
    override val peso: Int?,
    override val porcentajeEnergetico: PorcentajeEnergeticoDomain?
) : Ingrediente

// domain/model/PorcentajeEnergetico.kt
data class PorcentajeEnergeticoDomain(
    override val energia: Int,
    override val proteinas: Int,
    override val carbohidratos: Int,
    override val grasas: Int
) : PorcentajeEnergetico

// domain/model/InformacionNutricional.kt
data class InformacionNutricionalDomain(
    override val energia: Int,
    override val proteinas: Int,
    override val carbohidratos: Int,
    override val grasas: Int
) : InformacionNutricional

// domain/model/Resultado.kt
data class ResultadoDomain(
    override val nombre: String,
    override val pasos: List<PasoDomain>
) : Resultado

// domain/model/Paso.kt
data class PasoDomain(
    override val verbo: String,
    override val ingredientes: List<IngredientePasoDomain>,
    override val herramienta: String?,
    override val frase: String,
    override val tiempo: Int?,
    override val numero: Int
) : Paso

// domain/model/IngredientePaso.kt
data class IngredientePasoDomain(
    override val nombre: String,
    override val cantidad: String?,
    override val peso: Int?
): IngredientePaso