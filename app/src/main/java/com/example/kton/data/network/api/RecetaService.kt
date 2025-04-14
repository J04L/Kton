package com.example.kton.data.network.api

import com.example.kton.data.network.models.RecetaResponse
import com.example.kton.domain.model.RecetaDomain
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RecetaService {
    //cargar recetas
    @GET ("recetas") suspend fun getRecetas(
        @Query("filtros") filtros: String? = null,
        @Query("page") pagina: Int? = null,
        @Query("limit") limit: Int? = null
    ): Response<List<RecetaResponse>>

    //cargar receta por id
    @GET ("recetas/{id}") suspend fun getReceta(
        @Path("id") id: String
    ): Response<RecetaResponse>

    //crear receta
    @POST("recetas") suspend fun postReceta(receta: RecetaDomain): Response<RecetaResponse>

    //modificar receta
    @PUT ("recetas/{id}") suspend fun putReceta(
        @Path("id") id: String
    ): Response<RecetaResponse>

    //eliminar receta
    suspend fun deleteReceta(): Result<RecetaDomain>
    @DELETE ("recetas/{id}") suspend fun deleteReceta(
        @Path("id") id: String
    ): Response<RecetaResponse>
}