package com.zenjob.android.tmdb.common.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiConfiguration(val images: ApiImages)

@JsonClass(generateAdapter = true)
data class ApiImages(
    @field:Json(name = "secure_base_url") val baseUrl: String?,
    @field:Json(name = "poster_sizes") val posterSizes: List<String>?
)
