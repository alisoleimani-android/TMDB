package com.zenjob.android.tmdb.common.presentation.model.mappers

import com.zenjob.android.tmdb.common.domain.model.Configuration
import com.zenjob.android.tmdb.common.domain.model.Images
import com.zenjob.android.tmdb.common.presentation.model.UiConfiguration
import com.zenjob.android.tmdb.common.presentation.model.UiImages
import javax.inject.Inject

class UiConfigurationMapper @Inject constructor(
    private val mapper: UiImagesMapper
) : UiMapper<Configuration, UiConfiguration> {
    override fun mapToView(domainEntity: Configuration) = UiConfiguration(
        images = mapper.mapToView(domainEntity.images)
    )
}

class UiImagesMapper @Inject constructor() : UiMapper<Images, UiImages> {
    override fun mapToView(domainEntity: Images) = UiImages(
        baseUrl = domainEntity.baseUrl,
        posterSizes = domainEntity.posterSizes
    )
}