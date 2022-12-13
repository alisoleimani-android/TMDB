package com.zenjob.android.tmdb.common.domain.model

import java.util.*

data class Movie(
    val id: Long,
    val imdbId: String,
    val overview: String,
    val title: String,
    val posterPath: String,
    val releaseDate: Date,
    val voteAverage: Float
)
