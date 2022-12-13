package com.zenjob.android.tmdb.common.presentation.paging

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}