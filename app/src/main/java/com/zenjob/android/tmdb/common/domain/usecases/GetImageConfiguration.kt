package com.zenjob.android.tmdb.common.domain.usecases

import com.zenjob.android.tmdb.common.domain.model.Configuration

interface GetImageConfiguration {
    suspend operator fun invoke(): Result<Configuration>
}