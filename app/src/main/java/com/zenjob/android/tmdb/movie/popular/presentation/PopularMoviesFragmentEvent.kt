package com.zenjob.android.tmdb.movie.popular.presentation

sealed class PopularMoviesFragmentEvent {
    object LoadNextItems : PopularMoviesFragmentEvent()
    object RefreshList : PopularMoviesFragmentEvent()
}
