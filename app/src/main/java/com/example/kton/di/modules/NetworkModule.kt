package com.example.kton.di.modules

import android.content.Context
import com.example.kton.data.network.RecetasPagingSource
import com.example.kton.data.network.api.RecetaService
import com.example.kton.data.network.api.UsuarioService
import com.example.kton.data.network.interceptors.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideAuthInterceptor(
        @ApplicationContext context: Context
    ): AuthInterceptor {
        return AuthInterceptor(context)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
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
}