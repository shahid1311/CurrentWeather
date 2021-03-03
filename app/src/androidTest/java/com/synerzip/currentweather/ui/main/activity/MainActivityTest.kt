package com.synerzip.currentweather.ui.main.activity

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.synerzip.currentweather.util.DataBindingIdlingResource
import com.synerzip.currentweather.MainActivity
import com.synerzip.currentweather.R
import com.synerzip.currentweather.util.monitorActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private val appContext: Context by lazy {
        InstrumentationRegistry.getInstrumentation().targetContext
    }

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }


    /**
     * Test to see that toolbar is being visible as per requirements
     */
    @Test
    fun toolbar_nameVisible() {
        val scenario = ActivityScenario.launch<MainActivity>(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(scenario)
        Espresso.onView(ViewMatchers.withText(appContext.getString(R.string.app_name)))
            .check(matches(isDisplayed()))
    }


}