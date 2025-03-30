package com.tamersarioglu.vibestation.di

import com.tamersarioglu.vibestation.data.repository.FavoritesRepositoryImpl
import com.tamersarioglu.vibestation.data.repository.RadioRepositoryImpl
import com.tamersarioglu.vibestation.domain.repository.FavoritesRepository
import com.tamersarioglu.vibestation.domain.repository.RadioRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRadioRepository(
        repository: RadioRepositoryImpl
    ): RadioRepository

    @Binds
    @Singleton
    abstract fun bindFavoritesRepository(
        repository: FavoritesRepositoryImpl
    ): FavoritesRepository
}