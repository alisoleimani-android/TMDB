package com.zenjob.android.tmdb.movie.popular.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenjob.android.tmdb.common.presentation.ImageBaseUrlConfigurator
import com.zenjob.android.tmdb.common.presentation.model.Event
import com.zenjob.android.tmdb.common.presentation.model.PaginatedListUiState
import com.zenjob.android.tmdb.common.presentation.model.UiMovie
import com.zenjob.android.tmdb.common.presentation.model.mappers.UiMovieMapper
import com.zenjob.android.tmdb.common.presentation.paging.DefaultPaginator
import com.zenjob.android.tmdb.common.utils.createExceptionHandler
import com.zenjob.android.tmdb.movie.popular.domain.RequestNextPageOfPopularMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val requestNextPageOfPopularMovies: RequestNextPageOfPopularMovies,
    private val urlConfigurator: ImageBaseUrlConfigurator,
    private val mapper: UiMovieMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaginatedListUiState<UiMovie>())
    val uiState: StateFlow<PaginatedListUiState<UiMovie>> = _uiState

    private val exceptionHandler
        get() = viewModelScope.createExceptionHandler {
            _uiState.value = currentUiState.copy(loading = false, failure = Event(it))
        }

    private val currentUiState get() = uiState.value

    private val paginator = DefaultPaginator(
        initialKey = currentUiState.page,
        onLoadingUpdated = {
            _uiState.value = currentUiState.copy(loadingNewItems = it)
        },
        onRequest = { nextPage ->
            requestNextPageOfPopularMovies(page = nextPage)
        },
        getNextKey = {
            currentUiState.page + 1
        },
        onError = {
            _uiState.value = currentUiState.copy(failure = Event(it))
        },
        onSuccess = { newItems, newPage ->

            val imageBaseUrl = getImagesBaseUrl()

            val newUiItems = newItems.map {
                mapper.mapToView(it, imageBaseUrl)
            }

            _uiState.value = currentUiState.copy(
                items = currentUiState.items + newUiItems,
                page = newPage,
                noMoreItems = newItems.isEmpty()
            )
        }
    )

    init {
        loadNextItems()
    }

    fun onEvent(event: PopularMoviesFragmentEvent) {
        when (event) {
            PopularMoviesFragmentEvent.LoadNextItems -> {
                if (!currentUiState.noMoreItems && !currentUiState.loadingNewItems) loadNextItems()
            }

            PopularMoviesFragmentEvent.RefreshList -> refresh()
        }
    }

    private suspend fun getImagesBaseUrl() = withContext(exceptionHandler) {
        urlConfigurator.getListItemImageBaseUrl()
    }

    private fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    private fun refresh() {
        _uiState.value = currentUiState.copy(items = emptyList())
        paginator.reset()
        loadNextItems()
    }
}