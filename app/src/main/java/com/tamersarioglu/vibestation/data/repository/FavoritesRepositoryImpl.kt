package com.tamersarioglu.vibestation.data.repository

import com.tamersarioglu.vibestation.data.local.FavoriteStationDao
import com.tamersarioglu.vibestation.data.local.FavoriteStationEntity
import com.tamersarioglu.vibestation.domain.model.RadioStation
import com.tamersarioglu.vibestation.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val dao: FavoriteStationDao
) : FavoritesRepository {
    override fun getFavorites(): Flow<List<RadioStation>> {
        return dao.getAllFavorites().map { entities ->
            entities.map { it.toRadioStation() }
        }
    }

    override suspend fun addToFavorites(station: RadioStation) {
        dao.insertFavorite(FavoriteStationEntity.fromRadioStation(station))
    }

    override suspend fun removeFromFavorites(station: RadioStation) {
        dao.deleteFavorite(FavoriteStationEntity.fromRadioStation(station))
    }

    override suspend fun isFavorite(station: RadioStation): Boolean {
        return dao.isFavorite(station.id)
    }
} 