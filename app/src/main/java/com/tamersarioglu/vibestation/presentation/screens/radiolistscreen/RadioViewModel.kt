package com.tamersarioglu.vibestation.presentation.screens.radiolistscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamersarioglu.vibestation.domain.model.RadioStation
import com.tamersarioglu.vibestation.domain.repository.FavoritesRepository
import com.tamersarioglu.vibestation.domain.usecase.GetRadioStationsUseCase
import com.tamersarioglu.vibestation.presentation.common.UiState
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

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _filteredStations = MutableStateFlow<UiState<List<RadioStation>>>(UiState.Loading)
    val filteredStations: StateFlow<UiState<List<RadioStation>>> = _filteredStations.asStateFlow()

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
                    filterStations(stations, _searchQuery.value)
                }
            } catch (e: Exception) {
                _stations.value = UiState.Error(e.message ?: "Unknown error occurred")
                _filteredStations.value = UiState.Error(e.message ?: "Unknown error occurred")
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
            val currentFavorites = _favoriteStations.value.toMutableSet()
            if (currentFavorites.contains(station.id)) {
                currentFavorites.remove(station.id)
                favoritesRepository.removeFromFavorites(station)
            } else {
                currentFavorites.add(station.id)
                favoritesRepository.addToFavorites(station)
            }
            _favoriteStations.value = currentFavorites
        }
    }

    fun refreshStations() {
        loadStations()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        when (val currentStations = _stations.value) {
            is UiState.Success -> filterStations(currentStations.data, query)
            else -> _filteredStations.value = currentStations
        }
    }

    private fun filterStations(stations: List<RadioStation>, query: String) {
        if (query.isBlank()) {
            _filteredStations.value = UiState.Success(stations)
            return
        }

        val filtered = stations.filter { station ->
            station.name.contains(query, ignoreCase = true) ||
                    station.tags.split(",").any { tag ->
                        tag.trim().contains(query, ignoreCase = true)
                    }
        }

        _filteredStations.value = if (filtered.isEmpty()) {
            UiState.Empty
        } else {
            UiState.Success(filtered)
        }
    }
}