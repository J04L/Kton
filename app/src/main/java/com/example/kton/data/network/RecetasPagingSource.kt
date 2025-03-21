package com.example.kton.data.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kton.data.network.api.RecetaService
import com.example.kton.domain.model.Receta
import com.example.kton.domain.repository.toDomain
import javax.inject.Inject

class RecetasPagingSource @Inject constructor (
    private val recetaService: RecetaService,
    var filtros: String? = null
) : PagingSource<Int, Receta>() {
    override fun getRefreshKey(state: PagingState<Int, Receta>): Int? {
        // Obtiene la posición ancla (por ejemplo, el elemento central visible)
        val anchorPosition = state.anchorPosition ?: return null

        // Busca la página más cercana a esa posición
        val anchorPage = state.closestPageToPosition(anchorPosition)
        // Retorna la clave para refrescar, por ejemplo, ajustando prevKey o nextKey
        return anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Receta> {
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
                var recetasResponse = respuesta.body()?.map{ it.toDomain()}
                if (recetasResponse == null) throw Exception("ERROR el body de la respuesta es nulo")
                else recetasResponse
            }
            else throw Exception(respuesta.message())

            Log.d("PagingSource", "Load: page=$page, loadSize=${params.loadSize}, returned=${recetas.size}")
            LoadResult.Page(
                data = recetas,               // Lista que se obtuvo
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (recetas.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            // Si ocurre un error, devolvemos un LoadResult de error
            LoadResult.Error(e)
        }
    }

}
