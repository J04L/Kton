package com.example.kton.domain.repository.implementatios

import android.content.Context
import android.net.Uri
import com.example.kton.data.network.api.LogRegService
import com.example.kton.di.UserSession
import com.example.kton.domain.model.UsuarioDomain
import com.example.kton.domain.repository.RegisterRepository
import com.example.kton.utils.SecureTokenStorage
import com.example.kton.utils.getFileFromUri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerService : LogRegService,
    private val context: Context
) : RegisterRepository {
    override suspend fun register(
        username:String,
        email: String,
        pass: String,
        foto: Uri?,
        defaultFotoId: Int?
    ): Result<UsuarioDomain> {
        return try{
            val usernameRequest = username.toRequestBody("text/plain".toMediaTypeOrNull())
            val passwordRequest = pass.toRequestBody("text/plain".toMediaTypeOrNull())
            val emailRequest = email.toRequestBody("text/plain".toMediaTypeOrNull())

            val fotoRequest =
                if(foto!=null){
                    val mimeType = context.contentResolver.getType(foto) ?: "image/*" // Fallback genérico
                    val mediaType = mimeType.toMediaTypeOrNull()

                    val file = getFileFromUri(context, foto)

                    MultipartBody.Part.createFormData(
                        name = "foto",
                        filename = file.name,
                        body = file.asRequestBody(mediaType)
                    )
                }
                else if(defaultFotoId != null){
                    MultipartBody.Part.create(
                        body = defaultFotoId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    )
                }
                else throw Exception("Se debe elegir una foto de perfil")

            val response = registerService.register(
                username = usernameRequest,
                email = emailRequest,
                password = passwordRequest,
                foto = fotoRequest
            )

            if(response.isSuccessful){
                val usuario = response.body()?.usuario?.toDomain() ?: throw NullPointerException("Usuario de respuesta nulo")
                val token = response.body()?.token ?: throw NullPointerException("Token de respuesta nulo")
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