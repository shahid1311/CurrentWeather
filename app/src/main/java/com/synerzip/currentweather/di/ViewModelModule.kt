package com.synerzip.currentweather.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.synerzip.currentweather.ui.weather.current.CurrentWeatherViewModel
import com.synerzip.currentweather.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    /**
     * Creating [Map.Entry] for [CurrentWeatherViewModel] to be used in multiple
     * binding for grouping similar dependencies
     *
     * @param currentWeatherViewModel
     * @return [ViewModel]
     */
    @Binds
    @IntoMap
    @ViewModelKey(CurrentWeatherViewModel::class)
    abstract fun bindCurrentWeatherViewModel(currentWeatherViewModel: CurrentWeatherViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}