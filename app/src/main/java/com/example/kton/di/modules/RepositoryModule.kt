package com.example.kton.di.modules

import android.content.Context
import com.example.kton.data.network.api.LogRegService
import com.example.kton.data.network.api.RecetaService
import com.example.kton.data.network.api.SessionService
import com.example.kton.data.network.api.UsuarioService
import com.example.kton.domain.repository.LoginRepository
import com.example.kton.domain.repository.RecetaRepository
import com.example.kton.domain.repository.RegisterRepository
import com.example.kton.domain.repository.SessionRepository
import com.example.kton.domain.repository.implementatios.RecetaRepositoryImpl
import com.example.kton.domain.repository.UsuarioRepository
import com.example.kton.domain.repository.implementatios.LoginRepositoryImpl
import com.example.kton.domain.repository.implementatios.RegisterRepositoryImpl
import com.example.kton.domain.repository.implementatios.SessionRepositoryImpl
import com.example.kton.domain.repository.implementatios.UsuarioRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRecetaRepository(
        recetaService: RecetaService,
    ) : RecetaRepository{
        return RecetaRepositoryImpl(recetaService)
    }

    @Provides
    @Singleton
    fun provideUsuarioRepository(
        usuarioService: UsuarioService
    ): UsuarioRepository{
        return UsuarioRepositoryImpl(usuarioService)
    }

    @Provides
    @Singleton
    fun providerLoginRepository(
        loginService: LogRegService,
        @ApplicationContext context: Context
    ): LoginRepository{
        return LoginRepositoryImpl(loginService, context)
    }
    @Provides
    @Singleton
    fun providerRegisterRepository(
        loginService: LogRegService,
        @ApplicationContext context: Context,
    ): RegisterRepository{
        return RegisterRepositoryImpl(loginService, context)
    }
    @Provides
    @Singleton
    fun providerSessionRepository(
        sessionService: SessionService,
        @ApplicationContext context: Context,
    ): SessionRepository{
        return SessionRepositoryImpl(sessionService, context)
    }

}