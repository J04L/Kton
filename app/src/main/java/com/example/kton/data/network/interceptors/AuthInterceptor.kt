package com.example.kton.data.network.interceptors

import android.content.Context
import com.example.kton.data.network.api.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = TokenManager.getToken(context)
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
