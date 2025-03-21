package com.example.kton.di.modules

import com.example.kton.data.network.RecetasPagingSource
import com.example.kton.data.network.api.RecetaService
import com.example.kton.domain.repository.RecetaRepository
import com.example.kton.domain.repository.RecetaRepositoryImpl
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
        recetasPagingSource: RecetasPagingSource
    ) : RecetaRepository{
        return RecetaRepositoryImpl(recetaService, recetasPagingSource)
    }


}