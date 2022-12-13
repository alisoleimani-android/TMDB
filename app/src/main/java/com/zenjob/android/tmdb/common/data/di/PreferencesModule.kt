package com.zenjob.android.tmdb.common.data.di

import com.zenjob.android.tmdb.common.data.preferences.AppPreferences
import com.zenjob.android.tmdb.common.data.preferences.Preferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

    @Binds
    abstract fun providePreferences(preferences: AppPreferences): Preferences
}