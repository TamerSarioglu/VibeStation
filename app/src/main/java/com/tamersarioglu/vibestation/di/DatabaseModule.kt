package com.tamersarioglu.vibestation.di

import android.content.Context
import androidx.room.Room
import com.tamersarioglu.vibestation.data.local.AppDatabase
import com.tamersarioglu.vibestation.data.local.FavoriteStationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideFavoriteStationDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "favorite_stations.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteStationDao(database: AppDatabase): FavoriteStationDao {
        return database.favoriteStationDao
    }
} 