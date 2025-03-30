package com.tamersarioglu.vibestation.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tamersarioglu.vibestation.domain.model.RadioStation

@Entity(tableName = "favorite_stations")
data class FavoriteStationEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val streamUrl: String,
    val favicon: String?,
    val tags: String,
    val bitrate: Int
) {
    fun toRadioStation(): RadioStation {
        return RadioStation(
            id = id,
            name = name,
            streamUrl = streamUrl,
            favicon = favicon,
            tags = tags,
            bitrate = bitrate
        )
    }

    companion object {
        fun fromRadioStation(station: RadioStation): FavoriteStationEntity {
            return FavoriteStationEntity(
                id = station.id,
                name = station.name,
                streamUrl = station.streamUrl,
                favicon = station.favicon,
                tags = station.tags,
                bitrate = station.bitrate
            )
        }
    }
} 