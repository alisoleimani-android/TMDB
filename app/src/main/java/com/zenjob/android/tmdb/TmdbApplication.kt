package com.zenjob.android.tmdb

import android.app.Application
import com.zenjob.android.tmdb.appinitializers.AppInitializers
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * The main application class.
 * Initiate analytics, crashlytics, etc here...
 */
@HiltAndroidApp
class TmdbApplication : Application() {

    @Inject
    lateinit var initializers: AppInitializers

    override fun onCreate() {
        super.onCreate()
        initializers.init(this)
    }

}
