package com.zenjob.android.tmdb.common.data.api

import com.zenjob.android.tmdb.common.data.api.ApiConstants.CONFIGURATION_ENDPOINT
import com.zenjob.android.tmdb.common.data.api.ApiConstants.MOVIE_ENDPOINT
import com.zenjob.android.tmdb.common.data.api.ApiConstants.POPULAR_MOVIES_ENDPOINT
import com.zenjob.android.tmdb.common.data.api.ApiParameters.PARAM_LANGUAGE
import com.zenjob.android.tmdb.common.data.api.ApiParameters.PARAM_MOVIE_ID
import com.zenjob.android.tmdb.common.data.api.ApiParameters.PARAM_PAGE
import com.zenjob.android.tmdb.common.data.api.model.ApiConfiguration
import com.zenjob.android.tmdb.common.data.api.model.ApiMovie
import com.zenjob.android.tmdb.common.data.api.model.ApiPaginatedList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {

    @GET(POPULAR_MOVIES_ENDPOINT)
    suspend fun getPopularTvShows(
        @Query(PARAM_LANGUAGE) language: String? = null,
        @Query(PARAM_PAGE) page: Int? = null
    ): ApiPaginatedList<ApiMovie>

    @GET("$MOVIE_ENDPOINT/{$PARAM_MOVIE_ID}")
    suspend fun getDetails(
        @Path(PARAM_MOVIE_ID) movieId: Long,
        @Query(PARAM_LANGUAGE) query: String? = null
    ): ApiMovie

    @GET(CONFIGURATION_ENDPOINT)
    suspend fun getConfiguration(): ApiConfiguration

}
