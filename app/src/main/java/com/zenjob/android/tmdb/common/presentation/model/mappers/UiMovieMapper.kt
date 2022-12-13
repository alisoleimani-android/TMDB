package com.zenjob.android.tmdb.common.presentation.model.mappers

import com.zenjob.android.tmdb.common.domain.model.Movie
import com.zenjob.android.tmdb.common.presentation.model.UiMovie
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class UiMovieMapper @Inject constructor() {
    fun mapToView(domainEntity: Movie, imageBaseUrl: String): UiMovie {

        val releaseDateInStringFormat = try {
            with(SimpleDateFormat("E, dd MMM yyyy", Locale.getDefault())) {
                format(domainEntity.releaseDate)
            }
        } catch (t: Throwable) {
            t.printStackTrace()
            ""
        }

        return UiMovie(
            id = domainEntity.id,
            imdbId = domainEntity.imdbId,
            overview = domainEntity.overview,
            title = domainEntity.title,
            posterPath = imageBaseUrl + domainEntity.posterPath,
            releaseDate = releaseDateInStringFormat,
            voteAverage = domainEntity.voteAverage,
        )
    }
}