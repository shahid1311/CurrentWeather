package com.synerzip.currentweather.ui.weather.data.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponseModel(
    val coord: Coord? = null,
    val weather: List<Weather>? = null,
    val base: String? = null,
    val main: Main? = null,
    val visibility: Long? = null,
    val wind: Wind? = null,
    val clouds: Clouds? = null,
    val dt: Long? = null,
    val sys: SunTimings? = null,
    val id: Long? = null,
    val name: String? = null,
    val cod: Long? = null
)

data class Clouds(
    val all: Long? = null
)

data class Coord(
    val lon: Double? = null,
    val lat: Double? = null
)

data class Main(
    val temp: Double? = null,
    val pressure: Long? = null,
    val humidity: Long? = null,
    @SerializedName("temp_min") val tempMin: Double? = null,
    @SerializedName("temp_max") val tempMax: Double? = null,
    @SerializedName("feels_like") val feelsLike: Double? = null
)

data class Weather(
    val id: Long? = null,
    val main: String? = null,
    val description: String? = null,
    val icon: String? = null
)

data class Wind(
    val speed: Double? = null,
    val deg: Long? = null
)

data class SunTimings(
    val type: Int? = null,
    val id: Long? = null,
    val country: String? = null,
    val sunrise: Long? = null,
    val sunset: Long? = null
)
