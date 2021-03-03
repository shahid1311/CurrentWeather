package com.synerzip.currentweather

import android.app.Application
import com.synerzip.currentweather.di.AppComponent
import com.synerzip.currentweather.di.DaggerAppComponent

open class WeatherApplication : Application() {

    // Dagger main component, with lifecycle scope of application
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    //Initializing AppComponent
    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.builder().application(this).build()
    }

}