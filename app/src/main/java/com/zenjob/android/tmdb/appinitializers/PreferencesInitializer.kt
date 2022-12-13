package com.zenjob.android.tmdb.appinitializers

import android.app.Application
import com.zenjob.android.tmdb.common.data.preferences.AppPreferences
import javax.inject.Inject

class PreferencesInitializer @Inject constructor(
    private val prefs: AppPreferences
) : Initializer {
    override fun init(application: Application) {
        prefs.setup()
    }
}
