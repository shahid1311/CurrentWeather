package com.synerzip.currentweather.ui.weather.current

data class CurrentWeatherModel(
    val cityId: Long = 0,
    val cityName: String? = null,
    val maxMinTemp: String? = null,
    val temp: String? = null,
    val windDegree: String? = null,
    val windSpeed: String? = null,
    val description: String? = null,
    val iconCode: String? = null,
    val sunriseTime: String? = null,
    val sunsetTime: String? = null,
    val maxTemp: String? = null,
    val minTemp: String? = null,
    val feelsLikeTemp: String? = null,
    val atmosphericPressure: String? = null,
    val humidity: String? = null


) {
    override fun equals(other: Any?): Boolean {
        if (other !is CurrentWeatherModel) {
            return false
        }
        return cityId == other.cityId
    }

    override fun hashCode(): Int {
        return cityId.hashCode()
    }
}