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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val manageFavoritesUseCase: ManageFavoritesUseCase
) : ViewModel() {

    private val _favorites = MutableStateFlow<UiState<List<RadioStation>>>(UiState.Loading)
    val favorites: StateFlow<UiState<List<RadioStation>>> = _favorites.asStateFlow()
    
    // Track if we've already loaded favorites to avoid unnecessary reloads
    private var favoritesLoaded = false

    fun loadFavorites() {
        // Only load if not already loaded or explicitly asked to reload
        if (favoritesLoaded && _favorites.value !is UiState.Loading) {
            return
        }
        
        viewModelScope.launch {
            _favorites.value = UiState.Loading
            try {
                // Use collectLatest instead of collect to cancel previous collection if a new one starts
                manageFavoritesUseCase.getFavorites().collectLatest { stations ->
                    if (stations.isEmpty()) {
                        _favorites.value = UiState.Empty
                    } else {
                        _favorites.value = UiState.Success(stations)
                    }
                    favoritesLoaded = true
                }
            } catch (e: Exception) {
                _favorites.value = UiState.Error(e.message ?: "An error occurred")
                favoritesLoaded = false
            }
        }
    }

    fun removeFromFavorites(station: RadioStation) {
        viewModelScope.launch {
            manageFavoritesUseCase.removeFromFavorites(station)
            // No need to reload here as the Flow from the repository will emit new data
            // which will be handled by the existing collection in loadFavorites
        }
    }
} 