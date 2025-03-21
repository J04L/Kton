package com.example.kton.data.network.models

import com.google.gson.annotations.SerializedName

// data/network/model/RecetaResponse.kt
data class RecetaResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("ingredientesTotales") val ingredientesTotales: List<IngredienteResponse>,
    @SerializedName("informacionNutricional") val informacionNutricional: InformacionNutricionalResponse,
    @SerializedName("herramientasTotales") val herramientasTotales: List<String>,
    @SerializedName("tiempoTotal") val tiempoTotal: Int,
    @SerializedName("resultados") val resultados: List<ResultadoResponse>
)

// data/network/model/IngredienteResponse.kt
data class IngredienteResponse(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("cantidad") val cantidad: String?,
    @SerializedName("peso") val peso: Int?,
    @SerializedName("porcentajeEnergetico") val porcentajeEnergetico: PorcentajeEnergeticoResponse?
)

// data/network/model/PorcentajeEnergeticoResponse.kt
data class PorcentajeEnergeticoResponse(
    @SerializedName("energia") val energia: Int,
    @SerializedName("proteinas") val proteinas: Int,
    @SerializedName("carbohidratos") val carbohidratos: Int,
    @SerializedName("grasas") val grasas: Int
)

// data/network/model/InformacionNutricionalResponse.kt
data class InformacionNutricionalResponse(
    @SerializedName("energia") val energia: Int,
    @SerializedName("proteinas") val proteinas: Int,
    @SerializedName("carbohidratos") val carbohidratos: Int,
    @SerializedName("grasas") val grasas: Int
)

// data/network/model/ResultadoResponse.kt
data class ResultadoResponse(
    @SerializedName("nombreResultado") val nombreResultado: String,
    @SerializedName("pasos") val pasos: List<PasoResponse>
)

// data/network/model/PasoResponse.kt
data class PasoResponse(
    @SerializedName("verbo") val verbo: String,
    @SerializedName("ingredientes") val ingredientes: List<IngredientePasoResponse>,
    @SerializedName("herramienta") val herramienta: String?,
    @SerializedName("frase") val frase: String,
    @SerializedName("tiempo") val tiempo: Int?
)

// data/network/model/IngredientePasoResponse.kt
data class IngredientePasoResponse(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("cantidad") val cantidad: String?,
    @SerializedName("peso") val peso: Int?
)
