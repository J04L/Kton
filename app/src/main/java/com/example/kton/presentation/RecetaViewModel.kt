package com.example.kton.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kton.domain.model.RecetaDomain
import com.example.kton.domain.repository.RecetaRepository
import com.example.kton.presentation.models.RecetaUI
import com.example.kton.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RecetaViewModel @Inject constructor(
    private val recetaRepository: RecetaRepository
) : ViewModel() {

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    lateinit var recetaMostrada: RecetaDomain

    private val _navigationEvents = MutableSharedFlow<String>(
        replay = 1,          // Reenvía el último evento al nuevo colector
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val navigationEvents: SharedFlow<String> = _navigationEvents.asSharedFlow()

    val recetasPagingFlow : StateFlow<PagingData<RecetaUI>> = recetaRepository.getRecetasPaging(null)
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun recetaOnClick(receta: RecetaDomain){
        recetaMostrada = receta
        _navigationEvents.tryEmit(Screen.Receta.route)
    }
}