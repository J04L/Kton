package com.example.kton.di.modules

import com.example.kton.data.network.RecetasPagingSource
import com.example.kton.data.network.api.RecetaService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    //controla la respuesta
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) //desconecta si  tarda más de 30 en establecer conexión
            .readTimeout(30, TimeUnit.SECONDS) //desconecta si tarda más de 30 en leer datos
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/")
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
    fun provideRecetasPagingSource(recetaService: RecetaService): RecetasPagingSource{
        return RecetasPagingSource(recetaService)
    }
}