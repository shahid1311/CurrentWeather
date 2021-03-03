package com.synerzip.currentweather.ui.weather.data

import com.synerzip.currentweather.ui.weather.data.model.CurrentWeatherResponseModel

interface WeatherRepository {
    suspend fun fetchCurrentWeather(cityName: String): CurrentWeatherResponseModel?
}