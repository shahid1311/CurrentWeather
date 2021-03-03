package com.synerzip.currentweather.weather.data

import com.google.common.truth.Truth
import com.synerzip.currentweather.`interface`.APIServiceMock
import com.synerzip.currentweather.constant.APIResponseState
import com.synerzip.currentweather.network.WeatherNetworkService
import com.synerzip.currentweather.ui.weather.data.NetworkWeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior

@RunWith(MockitoJUnitRunner::class)
class NetworkWeatherRepositoryTest {
    @Mock
    private lateinit var mockRetrofit: Retrofit
    private lateinit var repository: NetworkWeatherRepository
    private lateinit var apiServiceMock: APIServiceMock
    private val behavior = NetworkBehavior.create()


    /**
     * Mocks the behaviour of the Retrofit. Uses [MockRetrofit] to achieve the same.
     * A [BehaviorDelegate] is used to mock the API Service used by Retrofit.
     *
     */
    @ExperimentalCoroutinesApi
    @Before
    fun createRepository() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://example.com").build()
        val mockRetrofit2 = MockRetrofit.Builder(retrofit)
            .networkBehavior(behavior).build()

        val delegate: BehaviorDelegate<WeatherNetworkService?> =
            mockRetrofit2.create(WeatherNetworkService::class.java)

        apiServiceMock = APIServiceMock(delegate)
        Mockito.`when`(mockRetrofit.create(WeatherNetworkService::class.java))
            .thenReturn(apiServiceMock)
        repository = NetworkWeatherRepository(this.mockRetrofit)
    }


    @Test
    fun fetchCurrentWeather_success() = runBlocking {
        val data = repository.fetchCurrentWeather("London")
        Truth.assertThat(data?.dt).isNotNull()
    }

    @Test
    fun fetchCurrentWeather_errorNotFound() = runBlocking {
        apiServiceMock.apiResponseState = APIResponseState.NOT_FOUND
        try {
            repository.fetchCurrentWeather("abc")
            Assert.fail("Should have thrown exception")
        } catch (e: NetworkWeatherRepository.RepositoryException) {
            Truth.assertThat(e.message).isEqualTo("Resource not found")
        }
    }

    @Test
    fun fetchCurrentWeather_errorAPIKey() = runBlocking {
        apiServiceMock.apiResponseState = APIResponseState.API_KEY_ERROR
        try {
            repository.fetchCurrentWeather("London")
            Assert.fail("Should have thrown exception")
        } catch (e: NetworkWeatherRepository.RepositoryException) {
            Truth.assertThat(e.message).isEqualTo("Invalid API key")
        }
    }

    @Test
    fun fetchCurrentWeather_errorNetwork() = runBlocking {
        apiServiceMock.apiResponseState = APIResponseState.NETWORK_ERROR
        try {
            repository.fetchCurrentWeather("London")
            Assert.fail("Should have thrown exception")
        } catch (e: NetworkWeatherRepository.RepositoryException) {
            Truth.assertThat(e.message)
                .isEqualTo("Unable to connect to our servers, check your Internet Connection.")
        }
    }
}
