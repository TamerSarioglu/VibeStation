package com.tamersarioglu.vibestation.data.remote

import com.tamersarioglu.vibestation.data.model.RadioStationDto
import retrofit2.http.GET

interface RadioApi {
    @GET("stations/bycountry/Türkiye")
    suspend fun getStations(): List<RadioStationDto>
}