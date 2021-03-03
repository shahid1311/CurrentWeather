package com.synerzip.currentweather

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

/**
 * Custom Runner for providing [MyTestApplication] only for UI tests.
 * Thus, [MyTestApplication] will be available as [Application] under test environment.
 *
 * Look at the implementation of this at build.gradle file
 *
 */
class CustomTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, MyTestApplication::class.java.name, context)
    }
}