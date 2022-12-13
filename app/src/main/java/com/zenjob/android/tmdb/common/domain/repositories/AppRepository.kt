package com.zenjob.android.tmdb.common.domain.repositories

import com.zenjob.android.tmdb.common.domain.model.Configuration

interface AppRepository {
    suspend fun getImageConfiguration(): Result<Configuration>
}