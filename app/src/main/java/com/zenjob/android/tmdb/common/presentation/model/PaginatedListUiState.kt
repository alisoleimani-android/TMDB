package com.zenjob.android.tmdb.common.presentation.model

data class PaginatedListUiState<T>(
    val loading: Boolean = false,
    val loadingNewItems: Boolean = false,
    val items: List<T> = emptyList(),
    val failure: Event<Throwable>? = null,
    val noMoreItems: Boolean = false,
    val page: Int = 1
)