package com.zenjob.android.tmdb.common.data

import com.zenjob.android.tmdb.common.data.api.NetworkCallHandler
import com.zenjob.android.tmdb.common.data.api.TMDBApi
import com.zenjob.android.tmdb.common.data.api.model.ApiConfiguration
import com.zenjob.android.tmdb.common.data.api.model.mappers.ApiConfigurationMapper
import com.zenjob.android.tmdb.common.data.preferences.AppPreferences
import com.zenjob.android.tmdb.common.domain.model.Configuration
import com.zenjob.android.tmdb.common.domain.repositories.AppRepository
import kotlinx.datetime.*
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val api: TMDBApi,
    private val preferences: AppPreferences,
    private val networkCallHandler: NetworkCallHandler,
    private val apiConfigurationMapper: ApiConfigurationMapper
) : AppRepository {

    override suspend fun getImageConfiguration(): Result<Configuration> {
        val now = Clock.System.now()
        val expirationTime = Instant.fromEpochSeconds(preferences.getImageBaseUrlExpirationTime())
        val apiConfiguration = preferences.getImageConfiguration()

        return if (now < expirationTime && apiConfiguration != null) {
            Result.success(apiConfigurationMapper.mapToDomain(apiConfiguration))
        } else {
            networkCallHandler.call {
                val apiEntity = api.getConfiguration()
                storeNewImageConfiguration(apiEntity)
                apiConfigurationMapper.mapToDomain(apiEntity)
            }
        }
    }

    private fun storeNewImageConfiguration(apiEntity: ApiConfiguration) {
        preferences.putImageConfiguration(apiEntity)

        val now = Clock.System.now()
        val systemTZ = TimeZone.currentSystemDefault()
        val fiveDaysLater = now.plus(5, DateTimeUnit.DAY, systemTZ)
        preferences.putImageBaseUrlExpirationTime(fiveDaysLater.epochSeconds)
    }
}