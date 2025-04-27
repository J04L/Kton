package com.example.kton.data.network.models.responses

import com.example.kton.domain.model.interfaces.InformacionNutricional
import com.example.kton.domain.model.interfaces.Ingrediente
import com.example.kton.domain.model.interfaces.IngredientePaso
import com.example.kton.domain.model.interfaces.Paso
import com.example.kton.domain.model.interfaces.PorcentajeEnergetico
import com.example.kton.domain.model.interfaces.Receta
import com.example.kton.domain.model.interfaces.Resultado
import com.google.gson.annotations.SerializedName

// data/network/model/RecetaResponse.kt
data class RecetaResponse(
    @SerializedName("_id") override val id: String,
    @SerializedName("titulo") override val titulo: String,
    @SerializedName("descripcion") override val descripcion: String,
    @SerializedName("ingredientesTotales") override val ingredientes: List<IngredienteResponse>,
    @SerializedName("informacionNutricional") override val informacionNutricional: InformacionNutricionalResponse,
    @SerializedName("herramientasTotales") override val herramientas: List<String>,
    @SerializedName("tiempoTotal") override val tiempo: Int,
    @SerializedName("resultados") override val resultados: List<ResultadoResponse>,
    @SerializedName("etiquetas") override val etiquetas : List<String>,
    @SerializedName("restriccionesAlimentarias") override val restriccionesAlimentarias : List<String>
): Receta

// data/network/model/IngredienteResponse.kt
data class IngredienteResponse(
    @SerializedName("nombre") override val nombre: String,
    @SerializedName("cantidad") override val cantidad: String,
    @SerializedName("peso") override val peso: Int?,
    @SerializedName("porcentajeEnergetico") override val porcentajeEnergetico: PorcentajeEnergeticoResponse?
): Ingrediente

// data/network/model/PorcentajeEnergeticoResponse.kt
data class PorcentajeEnergeticoResponse(
    @SerializedName("energia") override val energia: Int,
    @SerializedName("proteinas") override val proteinas: Int,
    @SerializedName("carbohidratos") override val carbohidratos: Int,
    @SerializedName("grasas") override val grasas: Int
) : PorcentajeEnergetico

// data/network/model/InformacionNutricionalResponse.kt
data class InformacionNutricionalResponse(
    @SerializedName("energia") override val energia: Int,
    @SerializedName("proteinas") override val proteinas: Int,
    @SerializedName("carbohidratos") override val carbohidratos: Int,
    @SerializedName("grasas") override val grasas: Int
): InformacionNutricional

// data/network/model/ResultadoResponse.kt
data class ResultadoResponse(
    @SerializedName("nombreResultado") override val nombre: String,
    @SerializedName("pasos") override val pasos: List<PasoResponse>
): Resultado

// data/network/model/PasoResponse.kt
data class PasoResponse(
    @SerializedName("verbo") override val verbo: String,
    @SerializedName("ingredientes") override val ingredientes: List<IngredientePasoResponse>,
    @SerializedName("herramienta") override val herramienta: String?,
    @SerializedName("frase") override val frase: String,
    @SerializedName("tiempo") override val tiempo: Int?,
    @SerializedName("numero") override val numero: Int
): Paso

// data/network/model/IngredientePasoResponse.kt
data class IngredientePasoResponse(
    @SerializedName("nombre") override val nombre: String,
    @SerializedName("cantidad") override val cantidad: String?,
    @SerializedName("peso") override val peso: Int?
) : IngredientePaso
