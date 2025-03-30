package com.tamersarioglu.vibestation.domain.model

data class RadioStation(
    val id: String,
    val name: String,
    val streamUrl: String,
    val favicon: String?,
    val tags: String = "",
    val bitrate: Int = 128
)