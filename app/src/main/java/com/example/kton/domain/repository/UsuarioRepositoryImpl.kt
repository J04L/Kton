package com.example.kton.domain.repository

import com.example.kton.data.network.api.UsuarioService
import com.example.kton.data.network.models.UsuarioRequest
import com.example.kton.domain.model.Usuario
import retrofit2.Response
import javax.inject.Inject

class UsuarioRepositoryImpl @Inject constructor(
    private val usuarioService: UsuarioService
) : UsuarioRepository{
    override suspend fun getUsuario(id: String) : Result<Usuario>{
        return manejarRespuesta(usuarioService.getUsuario(id)) { respuesta ->
            respuesta.body()!!.toDomain()
        }
    }

    override suspend fun createUsuario(
        usuario: Usuario,
        password: String
    ): Result<Usuario> {

        return manejarRespuesta(usuarioService.postUsuario(UsuarioRequest(usuario, password))){ respuesta ->
            respuesta.body()!!.toDomain()
        }
    }
    private fun<T, R> manejarRespuesta(
        respuesta: Response<T>,
        transform: (Response<T>) -> R
    ): Result<R>{
        return try{
            if (respuesta.isSuccessful){
                val usuario = transform(respuesta)
                Result.success(usuario)
            }
            else Result.failure(Exception(respuesta.message()))
        } catch (e : Exception){
            Result.failure(e)
        }
    }

}