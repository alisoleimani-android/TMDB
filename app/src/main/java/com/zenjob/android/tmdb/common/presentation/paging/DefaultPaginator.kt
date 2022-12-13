package com.zenjob.android.tmdb.common.presentation.paging

class DefaultPaginator<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadingUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Result<List<Item>>,
    private inline val getNextKey: suspend (List<Item>) -> Key,
    private inline val onError: suspend (Throwable) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
) : Paginator<Key, Item> {

    private var currentKey = initialKey

    // Using this property for avoiding duplication of the request
    private var isMakingRequest = false

    override suspend fun loadNextItems() {
        if (isMakingRequest) return

        isMakingRequest = true

        onLoadingUpdated(true)

        val result = onRequest(currentKey)

        isMakingRequest = false

        val items = result.getOrElse {
            // If we faced with an error
            onError(it)
            onLoadingUpdated(false)
            return
        }

        // If the request was successful
        currentKey = getNextKey(items)
        onSuccess(items, currentKey)
        onLoadingUpdated(false)
    }

    override fun reset() {
        currentKey = initialKey
    }
}