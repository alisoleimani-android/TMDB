package com.zenjob.android.tmdb.common.data.preferences

import com.zenjob.android.tmdb.common.data.api.model.ApiConfiguration
import com.zenjob.android.tmdb.common.domain.model.Theme
import kotlinx.coroutines.flow.Flow

interface Preferences {

    fun setup()

    fun putImageConfiguration(apiEntity: ApiConfiguration)

    fun putImageBaseUrlExpirationTime(expirationTime: Long)

    fun getImageConfiguration(): ApiConfiguration?

    fun getImageBaseUrlExpirationTime(): Long

    var theme: Theme

    fun observeTheme(): Flow<Theme>

}