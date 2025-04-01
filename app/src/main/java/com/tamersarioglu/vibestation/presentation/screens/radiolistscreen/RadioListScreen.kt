package com.tamersarioglu.vibestation.presentation.screens.radiolistscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
import com.tamersarioglu.vibestation.presentation.components.StationSearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadioListScreen(
    viewModel: RadioViewModel = hiltViewModel(),
    navController: NavController,
    onStationClick: (RadioStation) -> Unit
) {
    val stationsState = viewModel.filteredStations.collectAsState()
    val favoriteStations = viewModel.favoriteStations.collectAsState()
    val searchQuery = viewModel.searchQuery.collectAsState()
    val listState = rememberLazyListState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            EnhancedHeader(
                title = "Radio Stations",
                subtitle = "Discover and play your favorite stations"
            )

            StationSearchBar(
                query = searchQuery.value,
                onQueryChange = viewModel::updateSearchQuery,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (stationsState.value) {
                    is UiState.Loading -> {
                        LoadingIndicator()
                    }
                    is UiState.Success -> {
                        val stations = (stationsState.value as UiState.Success<List<RadioStation>>).data
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            state = listState,
                            contentPadding = PaddingValues(bottom = 80.dp, top = 8.dp) // Space for bottom nav
                        ) {
                            items(
                                items = stations,
                                key = { it.id }
                            ) { station ->
                                RadioItem(
                                    station = station,
                                    onStationClick = { onStationClick(station) },
                                    onFavoriteClick = { viewModel.toggleFavorite(station) },
                                    isFavorite = favoriteStations.value.contains(station.id)
                                )
                            }
                        }
                    }
                    is UiState.Error -> {
                        ErrorView(
                            message = (stationsState.value as UiState.Error).message,
                            onRetry = viewModel::refreshStations
                        )
                    }
                    is UiState.Empty -> {
                        EmptyStateView(
                            message = if (searchQuery.value.isBlank())
                                "No radio stations available"
                            else "No stations found matching '${searchQuery.value}'"
                        )
                    }
                }
            }
        }
    }
}