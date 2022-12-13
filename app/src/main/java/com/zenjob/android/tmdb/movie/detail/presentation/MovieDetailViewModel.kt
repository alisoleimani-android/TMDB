package com.zenjob.android.tmdb.movie.detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenjob.android.tmdb.common.domain.model.Movie
import com.zenjob.android.tmdb.common.presentation.ImageBaseUrlConfigurator
import com.zenjob.android.tmdb.common.presentation.model.Event
import com.zenjob.android.tmdb.common.presentation.model.mappers.UiMovieMapper
import com.zenjob.android.tmdb.common.utils.createExceptionHandler
import com.zenjob.android.tmdb.movie.detail.domain.GetMovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetail: GetMovieDetail,
    private val urlConfigurator: ImageBaseUrlConfigurator,
    private val mapper: UiMovieMapper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState

    private val movieId = savedStateHandle.get<Long>("movieId") ?: -1

    private val exceptionHandler
        get() = viewModelScope.createExceptionHandler {
            _uiState.value = uiState.value.copy(loading = false, failure = Event(it))
        }

    init {
        fetchMovieDetail()
    }

    fun onEvent(event: MovieDetailFragmentEvent) {
        when (event) {
            MovieDetailFragmentEvent.Refresh -> fetchMovieDetail()
        }
    }

    private fun fetchMovieDetail() {
        viewModelScope.launch(exceptionHandler) {

            _uiState.value = uiState.value.copy(loading = true)

            val imageBaseUrl = getImageBaseUrl()

            val movie: Movie = getMovieDetail(movieId).getOrThrow()

            _uiState.value = uiState.value.copy(
                loading = false,
                movie = mapper.mapToView(movie, imageBaseUrl)
            )
        }
    }

    private suspend fun getImageBaseUrl() = withContext(exceptionHandler) {
        urlConfigurator.getDetailImageBaseUrl()
    }

}