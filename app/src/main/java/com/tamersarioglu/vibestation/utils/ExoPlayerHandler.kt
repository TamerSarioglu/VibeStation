package com.tamersarioglu.vibestation.utils

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.tamersarioglu.vibestation.Utils.createExoPlayer
import com.tamersarioglu.vibestation.domain.model.RadioStation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExoPlayerHandler(private val context: Context) {
    private var _exoPlayer: ExoPlayer? = null
    private val exoPlayer: ExoPlayer?
        get() = _exoPlayer

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentStation = MutableStateFlow<RadioStation?>(null)
    val currentStation: StateFlow<RadioStation?> = _currentStation.asStateFlow()

    fun playStation(station: RadioStation) {
        // Release previous player if it exists
        releasePlayer()
        
        // Create and start new player
        _exoPlayer = createExoPlayer(context, station.streamUrl).apply {
            playWhenReady = true
            play()
            _isPlaying.value = true
            _currentStation.value = station
        }
    }

    fun stopStation() {
        // Stop and release player
        releasePlayer()
        _isPlaying.value = false
        _currentStation.value = null
    }

    fun togglePlayPause() {
        exoPlayer?.let { player ->
            if (player.isPlaying) {
                player.pause()
                _isPlaying.value = false
            } else {
                player.play()
                _isPlaying.value = true
            }
        }
    }

    private fun releasePlayer() {
        exoPlayer?.let { player ->
            player.stop()
            player.release()
            _exoPlayer = null
        }
    }

    fun getPlayer(): ExoPlayer? = exoPlayer
} 