package com.example.kton.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kton.domain.model.Receta
import com.example.kton.domain.repository.RecetaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecetaViewModel @Inject constructor(
    private val recetaRepository: RecetaRepository
) : ViewModel() {
    private val _recetas = MutableStateFlow<List<Receta>>(listOf())
    val recetas: StateFlow<List<Receta>> = _recetas.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _receta = MutableStateFlow<Receta?>(null)
    val receta: StateFlow<Receta?> = _receta.asStateFlow()

    lateinit var recetasPagingFlow : StateFlow<PagingData<Receta>>

    init {
        recargarRecetas(null)
    }

    fun recargarRecetas(filtro: String?){
        recetasPagingFlow = recetaRepository.getRecetas(filtro)
            .cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    }
}