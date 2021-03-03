package com.synerzip.currentweather.ui.weather.current

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.synerzip.currentweather.ui.weather.data.NetworkWeatherRepository
import com.synerzip.currentweather.ui.weather.data.WeatherRepository
import com.synerzip.currentweather.ui.weather.data.model.CurrentWeatherResponseModel
import com.synerzip.currentweather.R
import com.synerzip.currentweather.network.ErrorResponseModel
import com.synerzip.currentweather.network.NOT_FOUND
import com.synerzip.currentweather.network.NO_INTERNET
import com.synerzip.currentweather.util.*
import kotlinx.coroutines.*
import javax.inject.Inject

class CurrentWeatherViewModel @Inject constructor(
    private val context: Application,
    private val repository: WeatherRepository
) : AndroidViewModel(context) {
    private val _toastMessage = MutableLiveData<String>()

    val toastMessage: LiveData<String>
        get() = _toastMessage

    private val _cityWeather = MutableLiveData<CurrentWeatherModel>()

    val cityWeather: LiveData<CurrentWeatherModel>
        get() = _cityWeather

    private val _loadingState = MutableLiveData<Boolean>(false)
    private val _dataLoaded = MutableLiveData<Boolean>(false)

    val isLoading: LiveData<Boolean>
        get() = _loadingState

    val isShowData: LiveData<Boolean>
        get() = _dataLoaded

    /**
     * Fetch the weather for the city name.
     *
     * Any error in the individual response is added to the list of exceptions. Which is later used
     * to update the UI.
     * @param city - city name
     */
    private fun fetchWeatherForecast(city: String) {
        launchDataLoad { asyncScope ->
            asyncScope.run {
                var exceptionCity: ErrorModel? = null
                val responseModel =
                    try {
                        repository.fetchCurrentWeather(city)
                    } catch (e: NetworkWeatherRepository.RepositoryException.CurrentWeatherException) {
                        exceptionCity = ErrorModel(city, e.errorResponseModel)
                        null
                    }

                if (responseModel!=null) {
                    _cityWeather.value = transformModel(responseModel)
                    _dataLoaded.value = true
                }

                if (exceptionCity != null){
                    throwExceptionForWeatherAPI(exceptionCity)
                    _dataLoaded.value = false
                }
            }
        }
    }

    /**
     * Transforms the response from API to a model which will be used to update UI
     *
     * @param responseList
     * @return [List] of [CurrentWeatherModel]
     */
    private fun transformModel(responseModel: CurrentWeatherResponseModel): CurrentWeatherModel {
        return responseModel.let {
            CurrentWeatherModel(
                cityId = it.id ?: 0,
                cityName = it.name ?: context.getString(R.string.unavailable),
                temp = it.main?.temp?.toString()?.toDegreeFormat()
                    ?: context.getString(R.string.unavailable),
                maxTemp = if (it.main?.tempMax != null) context.getString(
                    R.string.max_temp,
                    it.main.tempMax.toString().toDegreeFormat()
                ) else context.getString(R.string.unavailable),
                minTemp = if (it.main?.tempMin != null) context.getString(
                    R.string.min_temp,
                    it.main.tempMin.toString().toDegreeFormat()
                ) else context.getString(R.string.unavailable),
                feelsLikeTemp = if (it.main?.feelsLike != null) context.getString(
                    R.string.feels_like,
                    it.main.feelsLike.toString().toDegreeFormat()
                )
                else context.getString(R.string.unavailable),
                windDegree = it.wind?.deg?.toString() + " " + context.getString(R.string.degree)
                    ?: context.getString(R.string.unavailable),
                windSpeed = it.wind?.speed?.toString()?.toWindFormat()
                    ?: context.getString(R.string.unavailable),
                description = it.weather?.getOrNull(0)?.description?.toTitleCase()
                    ?: context.getString(R.string.unavailable),
                iconCode = it.weather?.getOrNull(0)?.icon,
                sunriseTime = if (it.sys?.sunrise != null) context.getString(
                    R.string.sunrise,
                    getHoursMinutes(it.sys.sunrise)
                ) else context.getString(R.string.unavailable),
                sunsetTime = if (it.sys?.sunset != null) context.getString(
                    R.string.sunset,
                    getHoursMinutes(it.sys.sunset)
                ) else context.getString(R.string.unavailable),
                atmosphericPressure = it.main?.pressure?.toString()?.toPressureFormat()
                    ?: context.getString(R.string.unavailable),
                humidity = it.main?.humidity?.toString()?.toHumidityFormat()
                    ?: context.getString(R.string.unavailable)
            )
        }
    }


    /**
     * Handle exceptions(to update UI) thrown by each API call.
     *
     * @param exceptionCities
     */
    private fun throwExceptionForWeatherAPI(exceptionCity: ErrorModel?) {
        val notFoundList = ArrayList<String>()
        val noInternetList = ArrayList<String>()
        val generalList = ArrayList<String>()
        exceptionCity?.let {
            when (it.errorResponseModel.errorCode) {
                NO_INTERNET -> noInternetList.add(it.city)
                NOT_FOUND -> notFoundList.add(it.city)
                else -> generalList.add(it.city)
            }
        }

        val errorString = getErrorMessage(notFoundList, R.string.weather_city_not_found) +
                "\n" + getErrorMessage(noInternetList, R.string.weather_city_no_internet) +
                "\n" + getErrorMessage(generalList, R.string.weather_city_error)

        if (errorString.isNotEmpty()) {
            throw NetworkWeatherRepository.RepositoryException.GeneralException(
                ErrorResponseModel(
                    message =
                    errorString.trim()
                )
            )
        }
    }

    private fun getErrorMessage(errorCities: List<String>, msgResId: Int): String {
        return if (errorCities.isNotEmpty()) {
            context.getString(
                msgResId,
                errorCities.reduceIndexed { pos, acc, s ->
                    if (pos == errorCities.size - 1) "$acc and $s" else "$acc, $s"
                })
        } else ""
    }

    /**
     * Handles the [CoroutineScope] with respect to the lifecycle of [AndroidViewModel].
     * Also maintains the loading state of UI. Which would otherwise to become boilerplate code.
     *
     * @param loadingLiveData
     * @param block
     * @return [Job] which is cancellable
     */
    private fun launchDataLoad(
        loadingLiveData: MutableLiveData<Boolean> = _loadingState,
        block: suspend (coroutineScope: CoroutineScope) -> Unit
    ): Job {
        loadingLiveData.value = true
        return viewModelScope.launch {
            try {
                block(this)
            } catch (e: NetworkWeatherRepository.RepositoryException) {
                _dataLoaded.value = false
                _toastMessage.value = if (e.errorResponseModel.msgRes != null) {
                    context.getString(e.errorResponseModel.msgRes)
                } else {
                    e.message
                }
            } finally {
                loadingLiveData.value = false
            }
        }

    }

    /**
     * Take city, handle its validations and proceed to fetch the weather data.
     * @throws [IllegalArgumentException] if the city is not in the valid format or as per
     * requirements.
     *
     * @param city
     */
    @Throws(java.lang.IllegalArgumentException::class)
    fun fetchWeatherForecastForCity(city: String?) {
        val cityWeather = city?.let {
            fetchWeatherForecast(it.trim())
        }
    }

    data class ErrorModel(val city: String, val errorResponseModel: ErrorResponseModel)
}


