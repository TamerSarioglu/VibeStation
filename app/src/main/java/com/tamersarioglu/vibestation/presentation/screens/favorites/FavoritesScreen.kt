package com.tamersarioglu.vibestation.presentation.screens.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tamersarioglu.vibestation.domain.model.RadioStation
import com.tamersarioglu.vibestation.presentation.common.UiState
import com.tamersarioglu.vibestation.presentation.components.EmptyStateView
import com.tamersarioglu.vibestation.presentation.components.EnhancedHeader
import com.tamersarioglu.vibestation.presentation.components.ErrorView
import com.tamersarioglu.vibestation.presentation.components.LoadingIndicator
import com.tamersarioglu.vibestation.presentation.components.RadioItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    navController: NavController,
    onStationClick: (RadioStation) -> Unit
) {
    val favoritesState = viewModel.favorites.collectAsState()
    val listState = rememberLazyListState()

    // Load favorites when the screen is first displayed
    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            EnhancedHeader(
                title = "My Favorites",
                subtitle = "Your favorite radio stations"
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (val state = favoritesState.value) {
                    is UiState.Loading -> {
                        LoadingIndicator()
                    }

                    is UiState.Success -> {
                        val stations = state.data
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            state = listState,
                            contentPadding = PaddingValues(bottom = 80.dp) // Space for bottom nav
                        ) {
                            items(
                                items = stations,
                                key = { it.id }
                            ) { station ->
                                RadioItem(
                                    station = station,
                                    onStationClick = { onStationClick(station) },
                                    onFavoriteClick = { viewModel.removeFromFavorites(station) },
                                    isFavorite = true
                                )
                            }
                        }
                    }

                    is UiState.Error -> {
                        ErrorView(
                            message = state.message,
                            onRetry = viewModel::loadFavorites
                        )
                    }

                    is UiState.Empty -> {
                        EmptyStateView(
                            message = "Your favorites list is empty"
                        )
                    }
                }
            }
        }
    }
}
