package com.zenjob.android.tmdb.movie.detail.presentation

import com.zenjob.android.tmdb.common.presentation.model.Event
import com.zenjob.android.tmdb.common.presentation.model.UiMovie

data class MovieDetailUiState(
    val loading: Boolean = false,
    val movie: UiMovie? = null,
    val failure: Event<Throwable>? = null,
)
