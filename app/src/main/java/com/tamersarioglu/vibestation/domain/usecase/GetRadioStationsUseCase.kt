package com.tamersarioglu.vibestation.domain.usecase

import com.tamersarioglu.vibestation.domain.model.RadioStation
import com.tamersarioglu.vibestation.domain.repository.RadioRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRadioStationsUseCase @Inject constructor(
    private val repository: RadioRepository
) {
    operator fun invoke(): Flow<List<RadioStation>> {
        return repository.getStations()
    }
}