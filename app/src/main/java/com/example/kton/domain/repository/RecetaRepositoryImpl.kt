package com.example.kton.domain.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kton.data.network.RecetasPagingSource
import com.example.kton.data.network.api.RecetaService
import com.example.kton.data.network.models.InformacionNutricionalResponse
import com.example.kton.data.network.models.IngredientePasoResponse
import com.example.kton.data.network.models.PasoResponse
import com.example.kton.data.network.models.IngredienteResponse
import com.example.kton.data.network.models.PorcentajeEnergeticoResponse
import com.example.kton.data.network.models.RecetaResponse
import com.example.kton.data.network.models.ResultadoResponse
import com.example.kton.domain.model.InformacionNutricional
import com.example.kton.domain.model.IngredientePaso
import com.example.kton.domain.model.Paso
import com.example.kton.domain.model.PorcentajeEnergetico
import com.example.kton.domain.model.Receta
import com.example.kton.domain.model.Resultado
import com.example.kton.domain.model.Ingrediente
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import kotlin.Result

class RecetaRepositoryImpl @Inject constructor(
    private val recetaService: RecetaService,
    private val recetasPagingSource: RecetasPagingSource
) : RecetaRepository {
    override fun getRecetas(filtros: String?): Flow<PagingData<Receta>> {
        recetasPagingSource.filtros = filtros
        return Pager(
            config = PagingConfig(
                pageSize = 20,           // Cantidad de items por página
                enablePlaceholders = false
            ),
            pagingSourceFactory = { recetasPagingSource }
        ).flow
    }

    override suspend fun getReceta(id: String): Result<Receta> {
        return manejarRespuesta(recetaService.getReceta(id)) { response ->
            response.body()!!.toDomain()
        }
    }

    override suspend fun postReceta(receta: Receta): Result<Receta> {
        return manejarRespuesta(recetaService.postReceta(receta)) { response ->
            response.body()!!.toDomain()
        }
    }

    override suspend fun putReceta(id: String): Result<Receta> {
        return manejarRespuesta(recetaService.putReceta(id)) { response ->
            response.body()!!.toDomain()
        }
    }

    override suspend fun deleteReceta(id: String): Result<Receta> {
        return manejarRespuesta(recetaService.deleteReceta(id)) { response ->
            response.body()!!.toDomain()
        }
    }
}
// Extensión para convertir RecetaResponse a Receta
fun RecetaResponse.toDomain(): Receta {
    return Receta(
        id = this.id,
        titulo = this.titulo,
        descripcion = this.descripcion,
        ingredientes = this.ingredientesTotales.map { it.toDomain() },
        informacionNutricional = this.informacionNutricional.toDomain(),
        herramientas = this.herramientasTotales,
        tiempoTotal = this.tiempoTotal,
        resultados = this.resultados.map { it.toDomain() }
    )
}

fun IngredienteResponse.toDomain(): Ingrediente {
    return Ingrediente(
        nombre = this.nombre,
        cantidad = this.cantidad,
        peso = this.peso,
        porcentajeEnergetico = this.porcentajeEnergetico?.toDomain()
    )
}

fun PorcentajeEnergeticoResponse.toDomain(): PorcentajeEnergetico {
    return PorcentajeEnergetico(
        energia = this.energia,
        proteinas = this.proteinas,
        carbohidratos = this.carbohidratos,
        grasas = this.grasas
    )
}

fun InformacionNutricionalResponse.toDomain(): InformacionNutricional {
    return InformacionNutricional(
        energia = this.energia,
        proteinas = this.proteinas,
        carbohidratos = this.carbohidratos,
        grasas = this.grasas
    )
}

fun ResultadoResponse.toDomain(): Resultado {
    return Resultado(
        nombre = this.nombreResultado,
        pasos = this.pasos.map { it.toDomain() }
    )
}

fun PasoResponse.toDomain(): Paso {
    return Paso(
        verbo = this.verbo,
        ingredientes = this.ingredientes.map { it.toDomain() },
        herramienta = this.herramienta,
        frase = this.frase,
        tiempo = this.tiempo
    )
}

fun IngredientePasoResponse.toDomain(): IngredientePaso {
    return IngredientePaso(
        nombre = this.nombre,
        cantidad = this.cantidad,
        peso = this.peso
    )
}

fun <T, R> manejarRespuesta(
    response: Response<T>,
    transform: (Response<T>) -> R
): Result<R> {
    return try {
        if (response.isSuccessful) {
            response.body()?.let {
                Result.success(transform(response))
            } ?: Result.failure(Exception("Cuerpo de respuesta nulo"))
        } else {
            Result.failure(Exception(response.message()))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}