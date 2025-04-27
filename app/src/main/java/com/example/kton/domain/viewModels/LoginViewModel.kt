package com.example.kton.domain.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kton.domain.model.UsuarioDomain
import com.example.kton.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _succes = MutableStateFlow<Boolean>(false)
    val succes: StateFlow<Boolean> = _succes.asStateFlow()


    fun login(username: String, password: String){
        viewModelScope.launch {
            try {

                val result = loginRepository.login(username.trim(), password.trim())
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
    fun clearError() { _error.value = null }
}