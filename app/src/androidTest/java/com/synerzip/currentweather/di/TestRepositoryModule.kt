package com.synerzip.currentweather.di

import com.synerzip.currentweather.data.FakeWeatherRepository
import com.synerzip.currentweather.ui.weather.data.WeatherRepository
import dagger.Binds
import dagger.Module

@Module
abstract class TestRepositoryModule {

    /**
     * Provides mocked [WeatherRepository].
     *
     * @param repository
     * @return [WeatherRepository]
     */
    @Binds
    abstract fun provideRepository(repository: FakeWeatherRepository): WeatherRepository
}