package com.zenjob.android.tmdb.common.domain.usecases

import com.zenjob.android.tmdb.common.domain.model.Configuration
import com.zenjob.android.tmdb.common.domain.repositories.AppRepository
import javax.inject.Inject

class GetImageConfigurationImpl @Inject constructor(
    private val repository: AppRepository
) : GetImageConfiguration {

    override suspend fun invoke(): Result<Configuration> {
        return repository.getImageConfiguration()
    }
}