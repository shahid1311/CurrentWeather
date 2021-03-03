package com.synerzip.currentweather

import com.synerzip.currentweather.di.AppComponent
import com.synerzip.currentweather.di.DaggerTestAppComponent

/**
 * This Application class will used for UI tests
 */
class MyTestApplication : WeatherApplication() {
    /*
    Initializing TestAppComponent for dependency injection
     */
    override fun initializeComponent(): AppComponent {
        return DaggerTestAppComponent.builder().application(this).build()
    }
}