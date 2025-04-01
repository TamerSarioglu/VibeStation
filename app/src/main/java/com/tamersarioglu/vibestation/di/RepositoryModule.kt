package com.tamersarioglu.vibestation.di

import com.tamersarioglu.vibestation.data.repository.FavoritesRepositoryImpl
import com.tamersarioglu.vibestation.data.repository.RadioRepositoryImpl
import com.tamersarioglu.vibestation.domain.repository.FavoritesRepository
import com.tamersarioglu.vibestation.domain.repository.RadioRepository
import dagger.Binds
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
    fun bindRadioRepository(
        repository: RadioRepositoryImpl
    ): RadioRepository {
        return repository
    }

    @Provides
    @Singleton
    fun bindFavoritesRepository(
        repository: FavoritesRepositoryImpl
    ): FavoritesRepository{
        return repository
    }
}