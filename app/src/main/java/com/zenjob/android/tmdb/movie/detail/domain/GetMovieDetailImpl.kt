package com.zenjob.android.tmdb.movie.detail.domain

import com.zenjob.android.tmdb.common.domain.model.Movie
import com.zenjob.android.tmdb.common.domain.repositories.MovieRepository
import javax.inject.Inject

class GetMovieDetailImpl @Inject constructor(
    private val repository: MovieRepository
) : GetMovieDetail {

    override suspend fun invoke(movieId: Long, language: String?): Result<Movie> {
        return repository.getDetails(movieId, language)
    }
}