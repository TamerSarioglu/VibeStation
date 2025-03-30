package com.tamersarioglu.vibestation.domain.repository

import com.tamersarioglu.vibestation.domain.model.RadioStation
import kotlinx.coroutines.flow.Flow

interface RadioRepository {
    fun getStations(): Flow<List<RadioStation>>
}