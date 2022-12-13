package com.zenjob.android.tmdb.appinitializers

import android.app.Application
import com.zenjob.android.tmdb.BuildConfig
import com.zenjob.android.tmdb.common.utils.TmdbLogger
import javax.inject.Inject

class TimberInitializer @Inject constructor(
    private val logger: TmdbLogger
) : Initializer {
    override fun init(application: Application) = logger.setup(BuildConfig.DEBUG)
}
