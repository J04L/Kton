package com.example.kton.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kton.data.network.api.RecetaService
import com.example.kton.domain.repository.toDomain
import com.example.kton.presentation.models.RecetaUI
import kotlin.random.Random

class RecetasPagingSource(
    private val recetaService: RecetaService,
    var filtros: String? = null
) : PagingSource<Int, RecetaUI>() {
    override fun getRefreshKey(state: PagingState<Int, RecetaUI>): Int? {
        // Obtiene la posición ancla (por ejemplo, el elemento central visible)
        val anchorPosition = state.anchorPosition ?: return null

        // Busca la página más cercana a esa posición
        val anchorPage = state.closestPageToPosition(anchorPosition)
        // Retorna la clave para refrescar, por ejemplo, ajustando prevKey o nextKey
        return anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecetaUI> {
        // page = número de página que queremos cargar,
        // si la librería no lo sabe todavía, empieza por la 1
        val page = params.key ?: 1

        return try {
            // Tamaño de página.
            val pageSize = params.loadSize

            // Llamada a la API
            //en el filtro se va a enviar, el nombre de la receta
            //empezando con # las etiquetas
            //empezando por @ los ingredientes
            //palabras normales titulo
            val respuesta = recetaService.getRecetas(filtros = filtros, pagina = page, limit = pageSize)
            val recetas = if (respuesta.isSuccessful) {
                var recetasResponse= respuesta.body()?.map{ RecetaUI(it.toDomain(), getRandomSize()) } ?: throw Exception("ERROR el body de la respuesta es nulo")
                recetasResponse
            }
            else throw Exception(respuesta.message())

            val e = LoadResult.Page(
                data = recetas,               // Lista que se obtuvo
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (recetas.isEmpty()) null else page + 1
            )
            return e
        } catch (e: Exception) {
            // Si ocurre un error, devolvemos un LoadResult de error
            LoadResult.Error(e)
        }
    }

    private fun getRandomSize() : Int{
        val num = Random.nextInt(100)
        return if (num > 25) RecetaUI.SMALLSIZE
        else RecetaUI.LARGESIZE
    }
}
