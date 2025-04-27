package com.example.kton.domain.repository.implementatios

import com.example.kton.data.network.api.UsuarioService
import com.example.kton.domain.model.UsuarioDomain
import com.example.kton.domain.repository.UsuarioRepository
import retrofit2.Response
import javax.inject.Inject

class UsuarioRepositoryImpl @Inject constructor(
    private val usuarioService: UsuarioService
) : UsuarioRepository {
    override suspend fun getUsuario(id: String) : Result<UsuarioDomain>{
        return manejarRespuesta(usuarioService.getUsuario(id)) { respuesta ->
            respuesta.body()!!.toDomain()
        }
    }

    /*override suspend fun createUsuario(
        usuario: UsuarioDomain,
        password: String
    ): Result<UsuarioDomain> {

        return manejarRespuesta(usuarioService.postUsuario(RegisterRequest(usuario, password))){ respuesta ->
            respuesta.body()!!.toDomain()
        }
    }*/
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