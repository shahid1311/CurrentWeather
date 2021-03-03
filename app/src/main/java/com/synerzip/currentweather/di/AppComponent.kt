package com.synerzip.currentweather.di

import android.app.Application
import com.synerzip.currentweather.ui.weather.current.CurrentWeatherFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(weatherFragment: CurrentWeatherFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}