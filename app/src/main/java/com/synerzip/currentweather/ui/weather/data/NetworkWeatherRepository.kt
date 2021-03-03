package com.synerzip.currentweather.ui.weather.data

import com.synerzip.currentweather.network.ErrorResponseModel
import com.synerzip.currentweather.network.NetworkException
import com.synerzip.currentweather.ui.weather.data.model.CurrentWeatherResponseModel
import com.synerzip.currentweather.network.WeatherNetworkService
import com.synerzip.currentweather.network.await
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject

class NetworkWeatherRepository
@Inject constructor(private val retrofit: Retrofit) : WeatherRepository {
    private val retrofitService: WeatherNetworkService by lazy {
        retrofit.create(WeatherNetworkService::class.java)
    }

    /**
     * Fetch the weather for the city.
     * @throws [RepositoryException.CurrentWeatherException] for any response error from API.
     * @param cityName
     */
    @Throws(RepositoryException.CurrentWeatherException::class)
    override suspend fun fetchCurrentWeather(cityName: String): CurrentWeatherResponseModel? {
        return launchAPI({
            return@launchAPI retrofitService.getCurrentWeather(cityName)
        }, { e -> RepositoryException.CurrentWeatherException(e.errorResponseModel) })
    }


    /**
     * Generic method to accommodate all kind network requests called.
     *
     * @param apiCall
     * @param exception
     */
    private suspend fun <T : Any> launchAPI(
        apiCall: () -> Call<T>,
        exception: (e: NetworkException) -> RepositoryException
    ): T? {
        try {
            return apiCall().await()
        } catch (e: NetworkException) {
            throw exception(e)
        }
    }

    // Exceptions related to data requests.
    sealed class RepositoryException(val errorResponseModel: ErrorResponseModel) :
        Throwable(errorResponseModel.message) {
        class CurrentWeatherException(errorResponseModel: ErrorResponseModel) :
            RepositoryException(errorResponseModel)

        class WeatherForecastException(errorResponseModel: ErrorResponseModel) :
            RepositoryException(errorResponseModel)

        class GeneralException(errorResponseModel: ErrorResponseModel) :
            RepositoryException(errorResponseModel)
    }
}