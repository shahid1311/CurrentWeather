package com.synerzip.currentweather.network

import com.synerzip.currentweather.ui.weather.data.model.CurrentWeatherResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherNetworkService {

    /**
     * Call for getting current weather of particular city
     *
     * @param cityName
     * @return [Call]
     */
    @GET("weather")
    fun getCurrentWeather(@Query("q") cityName: String): Call<CurrentWeatherResponseModel>
}