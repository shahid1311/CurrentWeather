package com.synerzip.currentweather.util

import android.app.Activity
import com.synerzip.currentweather.di.AppComponent
import com.synerzip.currentweather.WeatherApplication

/**
 * Get the Dagger [AppComponent] for dependency injections. Extension of Activity.
 *
 * @return [AppComponent]
 */
fun Activity.getAppComponent(): AppComponent? {
    return (applicationContext as? WeatherApplication)?.appComponent
}