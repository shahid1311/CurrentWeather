package com.synerzip.currentweather.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * A implementation of [ViewModelProvider.Factory] which provides the requested [ViewModel].
 *
 * @property creaters injected by Dagger
 */
@Singleton
class ViewModelFactory @Inject constructor(
    private val creaters: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val creater = creaters[modelClass] ?: creaters.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value
        ?: throw IllegalArgumentException("${modelClass.simpleName} view model is unknown")
        @Suppress("UNCHECKED_CAST")
        return creater.get() as T
    }
}