package com.synerzip.currentweather.util

import android.annotation.SuppressLint

/**
 * Capitalize a string. For e.g.
 * ```
 * "clear sky".toTitleCase()
 * ```
 * will return "Clear Sky"
 *
 * @return Capitalized form of string
 */
@SuppressLint("DefaultLocale")
fun String.toTitleCase(): String? {
    return split(" ").joinToString(" ") { it.capitalize() }
}

/**
 * Convert a string in to degree format.
 *
 * @return
 */
fun String.toDegreeFormat(): String? {
    return this + "\u00B0"
}

/**
 * Add wind unit to string. Currently only supports '**m/s**' format
 *
 * @return
 */
fun String.toWindFormat(): String? {
    return "$this m/s"
}

/**
 * Add pressure format.
 *
 * @return
 */
fun String.toPressureFormat(): String? {
    return "$this hPa"
}

/**
 * Add humidity format.
 *
 * @return
 */
fun String.toHumidityFormat(): String? {
    return "$this %"
}