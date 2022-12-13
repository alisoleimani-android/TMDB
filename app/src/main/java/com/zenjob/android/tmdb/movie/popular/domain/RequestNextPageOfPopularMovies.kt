package com.zenjob.android.tmdb.movie.popular.domain

import com.zenjob.android.tmdb.common.domain.model.Movie

interface RequestNextPageOfPopularMovies {
    suspend operator fun invoke(page: Int? = null, language: String? = null): Result<List<Movie>>
}