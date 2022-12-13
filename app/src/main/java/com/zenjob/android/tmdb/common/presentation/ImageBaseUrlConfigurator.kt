package com.zenjob.android.tmdb.common.presentation

import com.zenjob.android.tmdb.common.domain.model.DETAIL_POSTER_SIZE
import com.zenjob.android.tmdb.common.domain.model.ImageConfigurationException
import com.zenjob.android.tmdb.common.domain.model.LIST_ITEM_POSTER_SIZE
import com.zenjob.android.tmdb.common.domain.usecases.GetImageConfiguration
import com.zenjob.android.tmdb.common.presentation.model.mappers.UiConfigurationMapper
import javax.inject.Inject

class ImageBaseUrlConfigurator @Inject constructor(
    private val getImageConfiguration: GetImageConfiguration,
    private val uiMapper: UiConfigurationMapper
) {

    suspend fun getListItemImageBaseUrl(): String {
        val uiConfiguration = uiMapper.mapToView(getImageConfiguration().getOrThrow())

        val baseUrl = uiConfiguration.images.baseUrl
        if (baseUrl.isEmpty()) throw ImageConfigurationException()

        val size = uiConfiguration.images.posterSizes.find { it == LIST_ITEM_POSTER_SIZE }
        if (size.isNullOrEmpty()) throw ImageConfigurationException()

        return "$baseUrl$size"
    }

    suspend fun getDetailImageBaseUrl(): String {
        val uiConfiguration = uiMapper.mapToView(getImageConfiguration().getOrThrow())

        val baseUrl = uiConfiguration.images.baseUrl
        if (baseUrl.isEmpty()) throw ImageConfigurationException()

        val size = uiConfiguration.images.posterSizes.find { it == DETAIL_POSTER_SIZE }
        if (size.isNullOrEmpty()) throw ImageConfigurationException()

        return "$baseUrl$size"
    }

}