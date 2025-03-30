package com.tamersarioglu.vibestation

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

object Utils {
    fun createExoPlayer(context: Context, streamUrl: String): ExoPlayer {
        return ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(streamUrl))
            prepare()
        }
    }
}