package com.example.kton.di.modules

import android.content.Context
import com.example.kton.data.network.RecetasPagingSource
import com.example.kton.data.network.api.LogRegService
import com.example.kton.data.network.api.RecetaService
import com.example.kton.data.network.api.SessionService
import com.example.kton.data.network.api.UsuarioService
import com.example.kton.data.network.interceptors.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideAuthInterceptor(
    ): AuthInterceptor {
        return AuthInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS // Para ver los headers
            // O usa Level.BODY para ver el cuerpo completo de la solicitud/respuesta
        }
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logging)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.33:5000/")
            .client(okHttpClient) //cliente inyectado por hilt
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRecetaService(retrofit: Retrofit): RecetaService{
        return retrofit.create(RecetaService::class.java) //retrofir inyectado por hilt
    }

    @Provides
    @Singleton
    fun provideUsuarioService(retrofit: Retrofit): UsuarioService{
        return retrofit.create(UsuarioService::class.java) //retrofir inyectado por hilt
    }

    @Provides
    @Singleton
    fun provideLogRegService(retrofit: Retrofit): LogRegService{
        return retrofit.create(LogRegService::class.java) //retrofir inyectado por hilt
    }

    @Provides
    @Singleton
    fun provideSessionService(retrofit: Retrofit): SessionService{
        return retrofit.create(SessionService::class.java) //retrofir inyectado por hilt
    }
}