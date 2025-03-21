package com.example.kton.domain.repository

import androidx.paging.PagingData
import com.example.kton.domain.model.Receta
import kotlinx.coroutines.flow.Flow

interface RecetaRepository {
    fun getRecetas(filtros : String?): Flow<PagingData<Receta>>
    suspend fun getReceta(id: String): Result<Receta>
    suspend fun postReceta(receta: Receta): Result<Receta>
    suspend fun putReceta(id: String): Result<Receta>
    suspend fun deleteReceta(id: String): Result<Receta>
}