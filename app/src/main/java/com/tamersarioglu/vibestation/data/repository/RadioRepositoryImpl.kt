package com.tamersarioglu.vibestation.data.repository

import com.tamersarioglu.vibestation.domain.model.RadioStation
import com.tamersarioglu.vibestation.domain.repository.RadioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RadioRepositoryImpl @Inject constructor() : RadioRepository {
    override fun getStations(): Flow<List<RadioStation>> = flow {
        // TODO: Replace with actual API call
        val stations = listOf(
            RadioStation(
                id = "1",
                name = "Radio Paradise",
                streamUrl = "https://stream.radioparadise.com/aac-128",
                favicon = "https://radioparadise.com/graphics/logo/rp_icon_400.png",
                tags = "eclectic,music",
                bitrate = 128
            ),
            RadioStation(
                id = "2",
                name = "SomaFM",
                streamUrl = "https://ice1.somafm.com/groovesalad-128-mp3",
                favicon = "https://somafm.com/img3/groovesalad-400.jpg",
                tags = "electronic,ambient",
                bitrate = 128
            )
        )
        emit(stations)
    }
}