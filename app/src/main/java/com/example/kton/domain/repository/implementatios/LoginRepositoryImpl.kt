package com.example.kton.domain.repository.implementatios

import android.content.Context
import javax.inject.Inject
import com.example.kton.data.network.api.LogRegService
import com.example.kton.data.network.models.requests.LoginRequest
import com.example.kton.di.UserSession
import com.example.kton.domain.model.UsuarioDomain
import com.example.kton.domain.repository.LoginRepository
import com.example.kton.utils.SecureTokenStorage
import org.json.JSONObject

class LoginRepositoryImpl @Inject constructor(
    private val loginService : LogRegService,
    private val context: Context
) : LoginRepository {

    override suspend fun login(username: String, password:String): Result<UsuarioDomain> {
        return try{
            val response = loginService.login(LoginRequest(username, password))
            if(response.isSuccessful){
                val usuario = response.body()?.usuario?.toDomain() ?: throw NullPointerException("Usuario de respuesta nulo")
                val token = response.body()?.token ?: throw NullPointerException("token de respuesta nulo")
                val actionToken = response.body()?.actionToken ?: throw NullPointerException("action_token de respuesta nulo")

                SecureTokenStorage.saveToken(context = context, token= token)
                UserSession.saveUser(usuario, actionToken)

                Result.success(usuario)
            } else {
                val errorJson = response.errorBody()?.string()
                val errorMessage = JSONObject(errorJson ?: "").optString("error", "Error desconocido")
                throw Exception(errorMessage)
            }
        } catch( e : Exception){
            Result.failure(e)
        }
    }
}