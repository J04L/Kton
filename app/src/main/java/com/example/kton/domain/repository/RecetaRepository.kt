package com.example.kton.domain.repository

import androidx.paging.PagingData
import com.example.kton.domain.model.RecetaDomain
import com.example.kton.presentation.models.RecetaUI
import kotlinx.coroutines.flow.Flow

interface RecetaRepository {
    fun getRecetasPaging(filtros : String?): Flow<PagingData<RecetaUI>>
    suspend fun getReceta(id: String): Result<RecetaDomain>
    suspend fun postReceta(receta: RecetaDomain): Result<RecetaDomain>
    suspend fun putReceta(id: String): Result<RecetaDomain>
    suspend fun deleteReceta(id: String): Result<RecetaDomain>
}