package com.zenjob.android.tmdb.common.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class ApiMovie(
    val id: Long,
    val imdbId: String?,
    val overview: String?,
    val title: String,
    @field:Json(name = "poster_path") val posterPath: String?,
    @field:Json(name = "release_date") val releaseDate: Date?,
    @field:Json(name = "vote_average") val voteAverage: Float?
)
