package com.synerzip.currentweather.di

import com.synerzip.currentweather.ui.weather.data.NetworkWeatherRepository
import com.synerzip.currentweather.ui.weather.data.WeatherRepository
import dagger.Binds
import dagger.Module
@Module
abstract class RepositoryModule {

    /**
     * Binds [NetworkWeatherRepository] to [WeatherRepository]
     * This is will be injected in view model constructor.
     *
     * @param repository
     * @return [WeatherRepository]
     */
    @Binds
    abstract fun provideRepository(repository: NetworkWeatherRepository): WeatherRepository
}