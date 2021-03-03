package com.synerzip.currentweather.data

import com.synerzip.currentweather.constant.APIResponseState.*
import com.synerzip.currentweather.R
import com.synerzip.currentweather.network.ErrorResponseModel
import com.synerzip.currentweather.network.NO_INTERNET
import com.synerzip.currentweather.ui.weather.data.NetworkWeatherRepository
import com.synerzip.currentweather.ui.weather.data.WeatherRepository
import com.synerzip.currentweather.ui.weather.data.model.CurrentWeatherResponseModel
import java.util.*
import javax.inject.Inject

/**
 * Fake [WeatherRepository] for testing.
 * All response states are mocked
 *
 */
class FakeWeatherRepository @Inject constructor() : WeatherRepository {
    var apiResponseState = SUCCESS

    /**
     * Mocking the response of current weather API.
     * set [apiResponseState] before calling this method to have desired response.
     *
     * @param cityName
     * @return [CurrentWeatherResponseModel]
     */
    override suspend fun fetchCurrentWeather(cityName: String): CurrentWeatherResponseModel? {
        when (apiResponseState) {
            SUCCESS -> {
                return CurrentWeatherResponseModel(
                    dt = 100000,
                    id = Random().nextLong(),
                    name = "London"
                )
            }
            API_KEY_ERROR -> {
                throw NetworkWeatherRepository.RepositoryException.CurrentWeatherException(
                    errorResponseModel = ErrorResponseModel(
                        msgRes = R.string.something_went_wrong,
                        errorCode = "401"
                    )
                )
            }
            NETWORK_ERROR -> {
                throw NetworkWeatherRepository.RepositoryException.CurrentWeatherException(
                    errorResponseModel = ErrorResponseModel(
                        msgRes = R.string.unreachable_server_internet_error,
                        errorCode = NO_INTERNET
                    )
                )
            }
            NOT_FOUND -> {
                throw NetworkWeatherRepository.RepositoryException.CurrentWeatherException(
                    errorResponseModel = ErrorResponseModel(
                        msgRes = R.string.something_went_wrong,
                        errorCode = com.synerzip.currentweather.network.NOT_FOUND
                    )
                )
            }
        }
    }
}
