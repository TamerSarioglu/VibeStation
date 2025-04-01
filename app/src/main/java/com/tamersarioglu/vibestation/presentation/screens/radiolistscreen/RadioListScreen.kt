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
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
import com.tamersarioglu.vibestation.Utils.createExoPlayer
import com.tamersarioglu.vibestation.domain.model.RadioStation
import com.tamersarioglu.vibestation.presentation.common.UiState
import com.tamersarioglu.vibestation.presentation.components.EmptyStateView
import com.tamersarioglu.vibestation.presentation.components.EnhancedHeader
import com.tamersarioglu.vibestation.presentation.components.ErrorView
import com.tamersarioglu.vibestation.presentation.components.LoadingIndicator
import com.tamersarioglu.vibestation.presentation.components.PlayingSectionModalSheet
import com.tamersarioglu.vibestation.presentation.components.RadioItem
import com.tamersarioglu.vibestation.presentation.components.StationSearchBar
import com.tamersarioglu.vibestation.utils.ExoPlayerHandler

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadioListScreen(
    viewModel: RadioViewModel = hiltViewModel(),
    navController: NavController
) {
    val stationsState = viewModel.filteredStations.collectAsState()
    val favoriteStations = viewModel.favoriteStations.collectAsState()
    val searchQuery = viewModel.searchQuery.collectAsState()
    var playingStation by remember { mutableStateOf<RadioStation?>(null) }
    val skipPartiallyExpanded by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)
    val context = LocalContext.current
    val listState = rememberLazyListState()
    val exoPlayerHandler = remember { ExoPlayerHandler(context) }

    // Keep track of the ExoPlayer instance
    var exoPlayer by remember { mutableStateOf<ExoPlayer?>(null) }

    // Clean up ExoPlayer when the composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer?.release()
        }
    }

    LaunchedEffect(playingStation) {
        if (playingStation != null) {
            // Release previous player if it exists
            exoPlayer?.release()
            // Create and start new player
            exoPlayer = createExoPlayer(context, playingStation!!.streamUrl).apply {
                playWhenReady = true
                play()
            }
            bottomSheetState.show()
        } else {
            // Stop and release player when no station is selected
            exoPlayer?.stop()
            exoPlayer?.release()
            exoPlayer = null
            bottomSheetState.hide()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Enhanced Header
            EnhancedHeader(
                title = "Radio Stations",
                subtitle = "Discover your favorite stations"
            )

            // Search bar below header
            StationSearchBar(
                query = searchQuery.value,
                onQueryChange = { viewModel.updateSearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp, bottom = 8.dp)
            )

            // Station List or Loading/Error states
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
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
                                    onStationClick = { playingStation = station },
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

        // Playing station bottom sheet
        playingStation?.let { station ->
            PlayingSectionModalSheet(
                station = station,
                onClose = {
                    playingStation = null
                },
                sheetState = bottomSheetState,
                exoPlayerHandler = exoPlayerHandler
            )
        }
    }
}