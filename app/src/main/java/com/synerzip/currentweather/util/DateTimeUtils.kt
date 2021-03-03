package com.synerzip.currentweather.util

import android.content.Context
import com.synerzip.currentweather.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val HOURS_MINUTES_FORMAT = "hh:mm a"
const val DATE_FORMAT = "dd MMM yyyy"
/**
 * Takes seconds since epoch and returns a formatted date.
 * If the date came to be for today or tomorrow. Localized text will be returned for the same.
 *
 * @param seconds since epoch
 * @param context required to get localized resources
 * @return Formatted date created from seconds
 */
fun getDateInUIFormat(seconds: Long, context: Context): String {
    val formatter: DateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = seconds * 1000
    val today = Calendar.getInstance()
    val tomorrow = Calendar.getInstance()
    tomorrow.add(Calendar.DATE, 1)
    return if (calendar[Calendar.YEAR] == today[Calendar.YEAR] && calendar[Calendar.DAY_OF_YEAR] == today[Calendar.DAY_OF_YEAR]
    ) {
        context.getString(R.string.today)
    } else if (calendar[Calendar.YEAR] == tomorrow[Calendar.YEAR] && calendar[Calendar.DAY_OF_YEAR] == tomorrow[Calendar.DAY_OF_YEAR]
    ) {
        context.getString(R.string.tomorrow)
    } else {
        formatter.format(calendar.time)
    }

}

/**
 * Takes seconds since epoch and returns a formatted time.
 *
 * @param seconds since epoch
 * @return Formatted time
 */
fun getTime(seconds: Long?): String? {
    return if (seconds != null) {
        val formatter: DateFormat = SimpleDateFormat("hh a", Locale.getDefault())
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = seconds * 1000
        formatter.format(calendar.time)
    } else null

}

/**
 * Takes seconds since epoch and returns a formatted time.
 *
 * @param seconds since epoch
 * @return Formatted time
 */
fun getHoursMinutes(epochTime: Long?): String? {
    return if (epochTime != null) {
        val formatter: DateFormat = SimpleDateFormat(HOURS_MINUTES_FORMAT, Locale.getDefault())
        val date = Date(epochTime*1000)
        formatter.format(date)
    } else null

}
