package com.tamersarioglu.vibestation.domain.repository

import com.tamersarioglu.vibestation.domain.model.RadioStation
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getFavorites(): Flow<List<RadioStation>>
    suspend fun addToFavorites(station: RadioStation)
    suspend fun removeFromFavorites(station: RadioStation)
    suspend fun isFavorite(station: RadioStation): Boolean
} 