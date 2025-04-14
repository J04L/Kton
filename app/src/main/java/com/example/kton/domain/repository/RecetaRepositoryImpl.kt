package com.example.kton.domain.repository

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
import com.example.kton.domain.model.InformacionNutricionalDomain
import com.example.kton.domain.model.IngredientePasoDomain
import com.example.kton.domain.model.PasoDomain
import com.example.kton.domain.model.PorcentajeEnergeticoDomain
import com.example.kton.domain.model.RecetaDomain
import com.example.kton.domain.model.ResultadoDomain
import com.example.kton.domain.model.IngredienteDomain
import com.example.kton.presentation.models.RecetaUI
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import kotlin.Result

class RecetaRepositoryImpl @Inject constructor(
    private val recetaService: RecetaService,
) : RecetaRepository {
    override fun getRecetasPaging(filtros: String?): Flow<PagingData<RecetaUI>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,           // Cantidad de items por página
                enablePlaceholders = false
            ),
            pagingSourceFactory = { RecetasPagingSource(recetaService, filtros) }
        ).flow
    }

    override suspend fun getReceta(id: String): Result<RecetaDomain> {
        return manejarRespuesta(recetaService.getReceta(id)) { response ->
            response.body()!!.toDomain()
        }
    }

    override suspend fun postReceta(receta: RecetaDomain): Result<RecetaDomain> {
        return manejarRespuesta(recetaService.postReceta(receta)) { response ->
            response.body()!!.toDomain()
        }
    }

    override suspend fun putReceta(id: String): Result<RecetaDomain> {
        return manejarRespuesta(recetaService.putReceta(id)) { response ->
            response.body()!!.toDomain()
        }
    }

    override suspend fun deleteReceta(id: String): Result<RecetaDomain> {
        return manejarRespuesta(recetaService.deleteReceta(id)) { response ->
            response.body()!!.toDomain()
        }
    }
}
// Extensión para convertir RecetaResponse a Receta
fun RecetaResponse.toDomain(): RecetaDomain {
    return RecetaDomain(
        id = this.id,
        titulo = this.titulo,
        descripcion = this.descripcion,
        ingredientes = this.ingredientes.map { it.toDomain() },
        informacionNutricional = this.informacionNutricional.toDomain(),
        herramientas = this.herramientas,
        tiempo = this.tiempo,
        resultados = this.resultados.map { it.toDomain() },
        etiquetas = this.etiquetas,
        restriccionesAlimentarias = this.restriccionesAlimentarias
    )
}

fun IngredienteResponse.toDomain(): IngredienteDomain {
    return IngredienteDomain(
        nombre = this.nombre,
        cantidad = this.cantidad,
        peso = this.peso,
        porcentajeEnergetico = this.porcentajeEnergetico?.toDomain()
    )
}

fun PorcentajeEnergeticoResponse.toDomain(): PorcentajeEnergeticoDomain {
    return PorcentajeEnergeticoDomain(
        energia = this.energia,
        proteinas = this.proteinas,
        carbohidratos = this.carbohidratos,
        grasas = this.grasas
    )
}

fun InformacionNutricionalResponse.toDomain(): InformacionNutricionalDomain {
    return InformacionNutricionalDomain(
        energia = this.energia,
        proteinas = this.proteinas,
        carbohidratos = this.carbohidratos,
        grasas = this.grasas
    )
}

fun ResultadoResponse.toDomain(): ResultadoDomain {
    return ResultadoDomain(
        nombre = this.nombre,
        pasos = this.pasos.map { it.toDomain() }
    )
}

fun PasoResponse.toDomain(): PasoDomain {
    return PasoDomain(
        verbo = this.verbo,
        ingredientes = this.ingredientes.map { it.toDomain() },
        herramienta = this.herramienta,
        frase = this.frase,
        tiempo = this.tiempo,
        numero = this.numero
    )
}

fun IngredientePasoResponse.toDomain(): IngredientePasoDomain {
    return IngredientePasoDomain(
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