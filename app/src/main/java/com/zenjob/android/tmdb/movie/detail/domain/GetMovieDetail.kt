package com.zenjob.android.tmdb.movie.detail.domain

import com.zenjob.android.tmdb.common.domain.model.Movie

interface GetMovieDetail {
    suspend operator fun invoke(movieId: Long, language: String? = null): Result<Movie>
}