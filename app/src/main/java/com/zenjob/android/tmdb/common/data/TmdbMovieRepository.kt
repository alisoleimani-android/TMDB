package com.zenjob.android.tmdb.common.data

import com.zenjob.android.tmdb.common.data.api.NetworkCallHandler
import com.zenjob.android.tmdb.common.data.api.TMDBApi
import com.zenjob.android.tmdb.common.data.api.model.mappers.ApiMovieMapper
import com.zenjob.android.tmdb.common.domain.model.Movie
import com.zenjob.android.tmdb.common.domain.repositories.MovieRepository
import javax.inject.Inject

class TmdbMovieRepository @Inject constructor(
    private val api: TMDBApi,
    private val apiMovieMapper: ApiMovieMapper,
    private val networkCallHandler: NetworkCallHandler
) : MovieRepository {

    override suspend fun getPopularTvShows(
        page: Int?,
        language: String?
    ): Result<List<Movie>> = networkCallHandler.call {
        api.getPopularTvShows(language, page).results.map { apiMovieMapper.mapToDomain(it) }
    }

    override suspend fun getDetails(
        movieId: Long,
        language: String?
    ): Result<Movie> = networkCallHandler.call {
        val apiMovie = api.getDetails(movieId, language)
        apiMovieMapper.mapToDomain(apiMovie)
    }
}