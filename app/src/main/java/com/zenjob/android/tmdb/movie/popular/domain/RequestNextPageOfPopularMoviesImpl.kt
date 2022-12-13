package com.zenjob.android.tmdb.movie.popular.domain

import com.zenjob.android.tmdb.common.domain.model.Movie
import com.zenjob.android.tmdb.common.domain.repositories.MovieRepository
import javax.inject.Inject

class RequestNextPageOfPopularMoviesImpl @Inject constructor(
    private val repository: MovieRepository
) : RequestNextPageOfPopularMovies {

    override suspend fun invoke(page: Int?, language: String?): Result<List<Movie>> {
        return repository.getPopularTvShows(page, language)
    }
}