package com.zenjob.android.tmdb.common.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UiMovie(
    val id: Long,
    val imdbId: String,
    val overview: String,
    val title: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Float
) : Parcelable
