package com.example.kton.di.modules

import com.example.kton.data.network.RecetasPagingSource
import com.example.kton.data.network.api.RecetaService
import com.example.kton.data.network.api.UsuarioService
import com.example.kton.domain.repository.RecetaRepository
import com.example.kton.domain.repository.RecetaRepositoryImpl
import com.example.kton.domain.repository.UsuarioRepository
import com.example.kton.domain.repository.UsuarioRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

}