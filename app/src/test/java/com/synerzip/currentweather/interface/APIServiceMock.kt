package com.synerzip.currentweather.`interface`

import com.synerzip.currentweather.constant.APIResponseState
import com.synerzip.currentweather.network.WeatherNetworkService
import com.synerzip.currentweather.ui.weather.data.model.CurrentWeatherResponseModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.Calls
import java.io.IOException

/**
 * Mocks the [WeatherNetworkService] for each API call and its different responses.
 *
 * @property delegate
 */
class APIServiceMock(private val delegate: BehaviorDelegate<WeatherNetworkService?>) :
    WeatherNetworkService {

    var apiResponseState = APIResponseState.SUCCESS


    /**
     * Mocking the response of current weather API.
     * set [apiResponseState] before calling this method to have desired response.
     *
     *
     * @param cityName
     * @return [Call]
     */
    override fun getCurrentWeather(cityName: String): Call<CurrentWeatherResponseModel> {
        return (when (apiResponseState) {
            APIResponseState.SUCCESS -> {
                delegate.returningResponse(
                    CurrentWeatherResponseModel(
                        dt = 1021212
                    )

                )
            }
            APIResponseState.NOT_FOUND -> {
                delegate.returning(
                    Calls.response(
                        Response.error<CurrentWeatherResponseModel>(
                            404,
                            ("{\"cod\":\"404\",\"message\":\"city not found\"}").toResponseBody("application/json".toMediaType())
                        )
                    )
                )
            }
            APIResponseState.API_KEY_ERROR -> {
                delegate.returning(
                    Calls.response(
                        Response.error<CurrentWeatherResponseModel>(
                            401,
                            ("{\"cod\":\"401\",\"message\":\"Invalid API Key\"}").toResponseBody("application/json".toMediaType())
                        )
                    )
                )
            }
            APIResponseState.NETWORK_ERROR -> {
                delegate.returning(Calls.failure<IOException>(IOException("Some error")))
            }
        })!!.run {
            this.getCurrentWeather(cityName)
        }
    }

}
