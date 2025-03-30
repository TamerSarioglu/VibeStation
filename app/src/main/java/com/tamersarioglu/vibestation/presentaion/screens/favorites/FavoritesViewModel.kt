package com.tamersarioglu.vibestation.presentaion.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamersarioglu.vibestation.domain.model.RadioStation
import com.tamersarioglu.vibestation.domain.repository.FavoritesRepository
import com.tamersarioglu.vibestation.presentaion.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _favorites = MutableStateFlow<UiState<List<RadioStation>>>(UiState.Loading)
    val favorites: StateFlow<UiState<List<RadioStation>>> = _favorites.asStateFlow()

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch {
            try {
                favoritesRepository.getFavorites().collect { stations ->
                    if (stations.isEmpty()) {
                        _favorites.value = UiState.Empty
                    } else {
                        _favorites.value = UiState.Success(stations)
                    }
                }
            } catch (e: Exception) {
                _favorites.value = UiState.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun removeFromFavorites(station: RadioStation) {
        viewModelScope.launch {
            favoritesRepository.removeFromFavorites(station)
        }
    }
} 