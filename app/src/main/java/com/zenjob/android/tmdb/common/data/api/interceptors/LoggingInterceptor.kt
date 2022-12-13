package com.zenjob.android.tmdb.common.data.api.interceptors

import com.zenjob.android.tmdb.common.utils.Logger
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class LoggingInterceptor @Inject constructor(
    private val logger: Logger
) : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        logger.i(message)
    }
}