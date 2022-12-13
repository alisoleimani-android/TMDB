package com.zenjob.android.tmdb.common.data.api.model.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*

class DateJsonAdapter {

    private val dateFormat = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault()).apply {
        timeZone = TimeZone.getDefault()
    }

    @Suppress("unused")
    @ToJson
    @Synchronized
    internal fun dateToJson(d: Date): String {
        return dateFormat.format(d)
    }

    @Suppress("unused")
    @FromJson
    @Synchronized
    internal fun dateToJson(s: String): Date {
        return try {
            dateFormat.parse(s)!!
        } catch (t: Throwable) {
            Date()
        }
    }
}
