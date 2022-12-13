package com.zenjob.android.tmdb.common.data.api.interceptors

import com.zenjob.android.tmdb.BuildConfig
import com.zenjob.android.tmdb.common.data.api.ApiParameters.PARAM_API_KEY
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class TmdbApiInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(PARAM_API_KEY, BuildConfig.TMDB_API_KEY)
            .build()

        val reqBuilder = original.newBuilder()
            .url(url)
        return chain.proceed(reqBuilder.build())
    }

}