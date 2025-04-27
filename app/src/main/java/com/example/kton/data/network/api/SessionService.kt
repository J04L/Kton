package com.example.kton.data.network.api

import com.example.kton.data.network.models.responses.SessionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header

interface SessionService {
    @GET("session/user")
    suspend fun userSession(@Header("Authorization") token: String): Response<SessionResponse>
}