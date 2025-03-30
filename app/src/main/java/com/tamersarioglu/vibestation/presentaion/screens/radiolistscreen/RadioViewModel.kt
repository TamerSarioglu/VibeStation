package com.tamersarioglu.vibestation.presentaion.screens.radiolistscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamersarioglu.vibestation.domain.model.RadioStation
import com.tamersarioglu.vibestation.domain.repository.FavoritesRepository
import com.tamersarioglu.vibestation.domain.repository.RadioRepository
import com.tamersarioglu.vibestation.domain.usecase.GetRadioStationsUseCase
import com.tamersarioglu.vibestation.presentaion.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RadioViewModel @Inject constructor(
    private val getRadioStationsUseCase: GetRadioStationsUseCase,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _stations = MutableStateFlow<UiState<List<RadioStation>>>(UiState.Loading)
    val stations: StateFlow<UiState<List<RadioStation>>> = _stations.asStateFlow()

    private val _favoriteStations = MutableStateFlow<Set<String>>(emptySet())
    val favoriteStations: StateFlow<Set<String>> = _favoriteStations.asStateFlow()

    init {
        loadStations()
        loadFavoriteStations()
    }

    fun loadStations() {
        viewModelScope.launch {
            _stations.value = UiState.Loading
            try {
                getRadioStationsUseCase().collect { stations ->
                    _stations.value = if (stations.isEmpty()) {
                        UiState.Empty
                    } else {
                        UiState.Success(stations)
                    }
                }
            } catch (e: Exception) {
                _stations.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    private fun loadFavoriteStations() {
        viewModelScope.launch {
            favoritesRepository.getFavorites().collect { stations ->
                _favoriteStations.value = stations.map { it.id }.toSet()
            }
        }
    }

    fun toggleFavorite(station: RadioStation) {
        viewModelScope.launch {
            if (_favoriteStations.value.contains(station.id)) {
                favoritesRepository.removeFromFavorites(station)
            } else {
                favoritesRepository.addToFavorites(station)
            }
        }
    }

    fun refreshStations() {
        loadStations()
    }
}