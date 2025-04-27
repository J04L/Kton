package com.example.kton.data.network.api

import com.example.kton.data.network.models.requests.LoginRequest
import com.example.kton.data.network.models.responses.LoginResponse
import com.example.kton.data.network.models.responses.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface LogRegService {
    @POST("session/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @Multipart
    @POST("session/register") suspend fun register(
        @Part ("foto") foto: MultipartBody.Part,
        @Part ("username") username: RequestBody?= null,
        @Part ("email") email: RequestBody?= null,
        @Part ("password") password: RequestBody?= null
    ): Response<RegisterResponse>

}
