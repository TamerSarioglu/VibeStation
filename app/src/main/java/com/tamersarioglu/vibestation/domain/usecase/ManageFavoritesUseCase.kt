package com.tamersarioglu.vibestation.domain.usecase

import com.tamersarioglu.vibestation.domain.model.RadioStation
import com.tamersarioglu.vibestation.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ManageFavoritesUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    fun getFavorites(): Flow<List<RadioStation>> {
        return repository.getFavorites()
    }

    suspend fun addToFavorites(station: RadioStation) {
        repository.addToFavorites(station)
    }

    suspend fun removeFromFavorites(station: RadioStation) {
        repository.removeFromFavorites(station)
    }

    suspend fun isFavorite(station: RadioStation): Boolean {
        return repository.isFavorite(station)
    }
} 