package com.tamersarioglu.vibestation.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteStationDao {
    @Query("SELECT * FROM favorite_stations")
    fun getAllFavorites(): Flow<List<FavoriteStationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(station: FavoriteStationEntity)

    @Delete
    suspend fun deleteFavorite(station: FavoriteStationEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_stations WHERE id = :stationId)")
    suspend fun isFavorite(stationId: String): Boolean
} 