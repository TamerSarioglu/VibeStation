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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    navController: NavController
) {
    val favoritesState = viewModel.favorites.collectAsState()
    var playingStation by remember { mutableStateOf<RadioStation?>(null) }
    val skipPartiallyExpanded by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)
    val context = LocalContext.current
    val listState = rememberLazyListState()

    // Load favorites only once when the screen is first composed
    LaunchedEffect(key1 = Unit) {
        viewModel.loadFavorites()
    }

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
            // Enhanced header for favorites
            EnhancedHeader(
                title = "My Favorites",
                subtitle = "Your favorite radio stations"
            )

            // Main content without animations
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
                                    onStationClick = { playingStation = station },
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

    // Playing station bottom sheet
    playingStation?.let { station ->
        PlayingSectionModalSheet(
            station = station,
            onClose = {
                playingStation = null
            },
            sheetState = bottomSheetState,
            exoPlayer = exoPlayer
        )
    }
}