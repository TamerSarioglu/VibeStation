package com.tamersarioglu.vibestation.presentation.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamersarioglu.vibestation.domain.model.RadioStation
import com.tamersarioglu.vibestation.domain.usecase.ManageFavoritesUseCase
import com.tamersarioglu.vibestation.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val manageFavoritesUseCase: ManageFavoritesUseCase
) : ViewModel() {

    private val _favorites = MutableStateFlow<UiState<List<RadioStation>>>(UiState.Loading)
    val favorites: StateFlow<UiState<List<RadioStation>>> = _favorites.asStateFlow()

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch {
            try {
                manageFavoritesUseCase.getFavorites().collect { stations ->
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
            manageFavoritesUseCase.removeFromFavorites(station)
        }
    }
} 