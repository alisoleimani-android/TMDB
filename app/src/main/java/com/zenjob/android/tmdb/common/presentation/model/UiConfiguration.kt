package com.zenjob.android.tmdb.common.presentation.model

data class UiConfiguration(val images: UiImages)

data class UiImages(val baseUrl: String, val posterSizes: List<String>)