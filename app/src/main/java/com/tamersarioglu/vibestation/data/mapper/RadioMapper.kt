package com.tamersarioglu.vibestation.data.mapper

import com.tamersarioglu.vibestation.data.model.RadioStationDto
import com.tamersarioglu.vibestation.domain.model.RadioStation

fun RadioStationDto.toRadioStation(): RadioStation {
    return RadioStation(
        id = stationuuid ?: "",
        name = name ?: "",
        streamUrl = urlResolved ?: url ?: "",
        favicon = favicon ?: "",
        tags = tags ?: "",
        bitrate = bitrate ?: 0
    )
} 