package com.tamersarioglu.vibestation.data.repository

import com.tamersarioglu.vibestation.data.mapper.toRadioStation
import com.tamersarioglu.vibestation.data.remote.RadioApi
import com.tamersarioglu.vibestation.domain.model.RadioStation
import com.tamersarioglu.vibestation.domain.repository.RadioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RadioRepositoryImpl @Inject constructor(
    private val api: RadioApi
) : RadioRepository {
    override fun getStations(): Flow<List<RadioStation>> = flow {
        try {
            val stations = api.getStations().map { it.toRadioStation() }
            emit(stations)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}