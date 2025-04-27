package com.example.kton.data.network.interceptors

import android.content.Context
import com.example.kton.di.UserSession
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor (

) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = UserSession.action_token
        val request = if (token != null){
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        }
        else {
            chain.request()
        }
        return chain.proceed(request)
    }
}
