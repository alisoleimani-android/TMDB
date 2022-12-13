package com.zenjob.android.tmdb.common.domain.model

data class Configuration(val images: Images)

data class Images(val baseUrl: String, val posterSizes: List<String>)

const val LIST_ITEM_POSTER_SIZE = "w342"
const val DETAIL_POSTER_SIZE = "w500"