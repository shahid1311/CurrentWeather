package com.synerzip.currentweather.network

import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call

/**
 * Sequential handling of Retrofit callback concept. Throws [NetworkException] in case of failure
 *
 * @param T
 * @return
 */
@Throws(NetworkException::class)
suspend fun <T : Any> Call<T>.await(): T? {
    return suspendCancellableCoroutine { continuation ->
        continuation.invokeOnCancellation {
            cancel()
        }
        enqueue(RetrofitCallback<T>(continuation))
    }
}