package com.example.kton.domain.viewModels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kton.data.network.api.LogRegService
import com.example.kton.domain.model.UsuarioDomain
import com.example.kton.domain.repository.LoginRepository
import com.example.kton.domain.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
) : ViewModel() {

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _succes = MutableStateFlow<Boolean>(false)
    val succes: StateFlow<Boolean> = _succes.asStateFlow()

    fun register(username: String, passwrod: String, email: String, foto: Uri?, defaultFotoId: Int?){
        viewModelScope.launch {
            try {
                val result = registerRepository.register(
                    username = username,
                    pass = passwrod,
                    email = email,
                    foto = foto,
                    defaultFotoId = defaultFotoId)
                result
                    .onSuccess {
                        _succes.value = true
                    }
                    .onFailure { exception ->
                        _succes.value = false
                        _error.value = exception.message
                    }

            }catch (e : Exception){
                _error.value = e.message
            }
        }
    }
}