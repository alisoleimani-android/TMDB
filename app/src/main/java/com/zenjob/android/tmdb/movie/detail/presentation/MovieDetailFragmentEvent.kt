package com.zenjob.android.tmdb.movie.detail.presentation

sealed class MovieDetailFragmentEvent {
    object Refresh : MovieDetailFragmentEvent()
}