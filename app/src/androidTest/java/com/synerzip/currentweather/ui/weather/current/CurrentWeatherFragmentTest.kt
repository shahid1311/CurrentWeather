package com.synerzip.currentweather.ui.weather.current

import android.content.Context
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.synerzip.currentweather.util.DataBindingIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class CurrentWeatherFragmentTest {
    private val appContext: Context by lazy {
        InstrumentationRegistry.getInstrumentation().targetContext
    }

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun registerIdlingResource() {
        //IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        //IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }


    /*@Test
    fun viewVisible() {
        val scenario =
            launchFragmentInContainer<CurrentWeatherFragment>(themeResId = R.style.AppTheme)
        dataBindingIdlingResource.monitorFragment(scenario)
        onView(withId(R.id.ti_search)).check(matches(isDisplayed()))
    }

    @Test
    fun initViewState() {
        val scenario =
            launchFragmentInContainer<CurrentWeatherFragment>(themeResId = R.style.AppTheme)
        dataBindingIdlingResource.monitorFragment(scenario)
        onView(withId(R.id.list)).check(RecyclerViewItemCountAssertion(0))
    }

    @Test
    fun searchCities_validate() {
        val scenario =
            launchFragmentInContainer<CurrentWeatherFragment>(themeResId = R.style.AppTheme)
        dataBindingIdlingResource.monitorFragment(scenario)
        onView(withId(R.id.et_search)).perform(
            clearText(),
            typeText("Ludhiana,New york,ABC"),
            pressImeActionButton()
        )
        onView(withId(R.id.list)).check(RecyclerViewItemCountAssertion(3))
    }

    @Test
    fun searchCities_listValidate() {
        val scenario =
            launchFragmentInContainer<CurrentWeatherFragment>(themeResId = R.style.AppTheme)
        dataBindingIdlingResource.monitorFragment(scenario)
        onView(withId(R.id.et_search)).perform(
            clearText(),
            typeText("Ludhiana,New york,ABC"),
            pressImeActionButton()
        )
        onView(withId(R.id.list))
            .perform(
                RecyclerViewActions.scrollToPosition<DaysForecastAdapter.ViewHolder>(0)
            )
        onView(allOf(isDisplayed(), withText("London")))
    }

    @Test
    fun searchCities_validationErrorsMin() {
        val scenario =
            launchFragmentInContainer<CurrentWeatherFragment>(themeResId = R.style.AppTheme)
        dataBindingIdlingResource.monitorFragment(scenario)
        onView(withId(R.id.et_search)).perform(
            clearText(),
            typeText("Ludhiana,New york"),
            pressImeActionButton()
        )
        onView(withText(appContext.getString(R.string.cities_min_error))).check(matches(isDisplayed()))
    }

    @Test
    fun searchCities_validationErrorsMax() {
        val scenario =
            launchFragmentInContainer<CurrentWeatherFragment>(themeResId = R.style.AppTheme)
        dataBindingIdlingResource.monitorFragment(scenario)
        onView(withId(R.id.et_search)).perform(
            clearText(),
            typeText("Ludhiana,New york,acd,das,port blair,jalandhar,luckhnow,Jamshedpur,New delhi"),
            pressImeActionButton()
        )
        onView(withText(appContext.getString(R.string.cities_max_error))).check(matches(isDisplayed()))
    }*/

}