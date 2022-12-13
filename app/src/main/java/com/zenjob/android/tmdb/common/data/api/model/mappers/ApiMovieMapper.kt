package com.zenjob.android.tmdb.common.data.api.model.mappers

import com.zenjob.android.tmdb.common.data.api.model.ApiMovie
import com.zenjob.android.tmdb.common.domain.model.Movie
import java.util.*
import javax.inject.Inject

class ApiMovieMapper @Inject constructor() : ApiMapper<ApiMovie, Movie> {
    override fun mapToDomain(apiEntity: ApiMovie): Movie = Movie(
        id = apiEntity.id,
        imdbId = apiEntity.imdbId.orEmpty(),
        overview = apiEntity.overview.orEmpty(),
        title = apiEntity.title,
        posterPath = apiEntity.posterPath.orEmpty(),
        releaseDate = apiEntity.releaseDate ?: Date(),
        voteAverage = apiEntity.voteAverage ?: 0f
    )
}