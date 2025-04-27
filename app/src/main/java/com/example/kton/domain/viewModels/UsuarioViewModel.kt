package com.example.kton.domain.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kton.domain.model.UsuarioDomain
import com.example.kton.domain.repository.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsuarioViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) : ViewModel(){
    lateinit var usuarioApp: UsuarioDomain
    var error : Exception? = null

    /*fun crearUsuario(usuario: UsuarioDomain, password: String){
        viewModelScope.launch{
            val result = usuarioRepository.createUsuario(usuario, password)
            if(result.isSuccess){
                usuarioApp = result.getOrNull()!!
            }
            else{
                error = Exception("No se ha podido crear el usuario")
            }
        }
    }*/
}