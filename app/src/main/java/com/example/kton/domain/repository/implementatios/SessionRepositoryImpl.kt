package com.example.kton.domain.repository.implementatios

import android.content.Context
import com.example.kton.data.network.api.SessionService
import com.example.kton.di.UserSession
import com.example.kton.domain.model.UsuarioDomain
import com.example.kton.domain.repository.SessionRepository
import com.example.kton.utils.SecureTokenStorage
import org.json.JSONObject
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    val sessionService: SessionService,
    val context: Context
): SessionRepository {
    override suspend fun userSession(sessionToken: String): Result<UsuarioDomain> {
        return try{
                val token = "Bearer $sessionToken"
                val response = sessionService.userSession(token)
                if (response.isSuccessful) {
                    val usuario = response.body()?.usuario?.toDomain() ?: throw NullPointerException(
                        "Usuario de respuesta nulo"
                    )
                    val actionToken = response.body()?.actionToken
                        ?: throw NullPointerException("action_token de respuesta nulo")

                    UserSession.action_token = actionToken
                    UserSession.user = usuario

                    Result.success(usuario)
                } else {
                    val errorJson = response.errorBody()?.string()
                    val errorMessage =
                        JSONObject(errorJson ?: "").optString("error", "Error desconocido")
                    throw Exception(errorMessage)
                }

        } catch (e : Exception){
            Result.failure(e)
        }
    }
}