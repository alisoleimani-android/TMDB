package com.zenjob.android.tmdb.common.data.api.model.mappers

import com.zenjob.android.tmdb.common.data.api.model.ApiConfiguration
import com.zenjob.android.tmdb.common.data.api.model.ApiImages
import com.zenjob.android.tmdb.common.domain.model.Configuration
import com.zenjob.android.tmdb.common.domain.model.Images
import javax.inject.Inject

class ApiConfigurationMapper @Inject constructor(
    private val mapper: ApiImagesMapper
) : ApiMapper<ApiConfiguration, Configuration> {
    override fun mapToDomain(apiEntity: ApiConfiguration) = Configuration(
        images = mapper.mapToDomain(apiEntity.images)
    )
}

class ApiImagesMapper @Inject constructor() : ApiMapper<ApiImages, Images> {
    override fun mapToDomain(apiEntity: ApiImages) = Images(
        baseUrl = apiEntity.baseUrl.orEmpty(),
        posterSizes = apiEntity.posterSizes.orEmpty()
    )
}