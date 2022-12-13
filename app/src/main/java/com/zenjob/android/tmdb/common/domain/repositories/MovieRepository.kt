package com.zenjob.android.tmdb.common.domain.repositories

import com.zenjob.android.tmdb.common.domain.model.Movie

interface MovieRepository {

    suspend fun getPopularTvShows(page: Int? = null, language: String? = null): Result<List<Movie>>

    suspend fun getDetails(movieId: Long, language: String? = null): Result<Movie>
}