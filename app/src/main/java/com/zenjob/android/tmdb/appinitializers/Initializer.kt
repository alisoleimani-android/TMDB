package com.zenjob.android.tmdb.appinitializers

import android.app.Application

interface Initializer {
    fun init(application: Application)
}
