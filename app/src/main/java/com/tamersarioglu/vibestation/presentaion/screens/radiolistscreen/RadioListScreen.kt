package com.tamersarioglu.vibestation.presentaion.screens.radiolistscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
import com.tamersarioglu.vibestation.Utils.createExoPlayer
import com.tamersarioglu.vibestation.domain.model.RadioStation
import com.tamersarioglu.vibestation.presentaion.common.UiState
import com.tamersarioglu.vibestation.presentaion.components.EmptyStateView
import com.tamersarioglu.vibestation.presentaion.components.ErrorView
import com.tamersarioglu.vibestation.presentaion.components.LoadingIndicator
import com.tamersarioglu.vibestation.presentaion.components.PlayingSectionModalSheet
import com.tamersarioglu.vibestation.presentaion.components.RadioItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadioListScreen(
    viewModel: RadioViewModel = hiltViewModel(),
    navController: NavController
) {
    val stationsState = viewModel.stations.collectAsState()
    var playingStation by remember { mutableStateOf<RadioStation?>(null) }
    val skipPartiallyExpanded by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)
    val context = LocalContext.current

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

    Box(modifier = Modifier.fillMaxSize()) {
        when (stationsState.value) {
            is UiState.Loading -> {
                LoadingIndicator()
            }
            is UiState.Success -> {
                val stations = (stationsState.value as UiState.Success<List<RadioStation>>).data
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(stations) { station ->
                        RadioItem(
                            station = station,
                            onStationClick = { playingStation = station },
                            onFavoriteClick = { viewModel.toggleFavorite(station) }
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
                    message = "No radio stations available"
                )
            }
        }

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
}

