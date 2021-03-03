package com.synerzip.currentweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.synerzip.currentweather.ui.weather.current.CurrentWeatherFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, CurrentWeatherFragment())
                    .commitNow()
        }
    }
}