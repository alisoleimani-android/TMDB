package com.zenjob.android.tmdb.common.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.zenjob.android.tmdb.common.data.api.model.ApiConfiguration
import com.zenjob.android.tmdb.common.data.preferences.PreferencesConstants.KEY_IMAGE_CONFIGURATION
import com.zenjob.android.tmdb.common.data.preferences.PreferencesConstants.KEY_IMG_BASE_URL_EXPIRATION_TIME
import com.zenjob.android.tmdb.common.data.preferences.PreferencesConstants.KEY_THEME
import com.zenjob.android.tmdb.common.domain.model.Theme
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(
    @ApplicationContext context: Context
) : Preferences {

    companion object {
        const val PREFERENCES_NAME = "TMDB_PREFERENCES"
    }

    private val defaultThemeValue = Theme.DARK.value

    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    private val preferenceKeyChangedFlow = MutableSharedFlow<String>(extraBufferCapacity = 1)

    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        preferenceKeyChangedFlow.tryEmit(key)
    }

    override var theme: Theme
        get() = getThemeForStorageKey(preferences.getString(KEY_THEME, defaultThemeValue).orEmpty())
        set(value) = edit { putString(KEY_THEME, value.value) }

    override fun setup() {
        preferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun putImageConfiguration(apiEntity: ApiConfiguration) {
        val adapter = Moshi.Builder().build().adapter(ApiConfiguration::class.java)
        val configurationBody = adapter.toJson(apiEntity)
        edit { putString(KEY_IMAGE_CONFIGURATION, configurationBody) }
    }

    override fun getImageConfiguration(): ApiConfiguration? {
        val configurationBody = preferences.getString(KEY_IMAGE_CONFIGURATION, "").orEmpty()
        return if (configurationBody.isNotEmpty()) {
            val adapter = Moshi.Builder().build().adapter(ApiConfiguration::class.java)
            adapter.fromJson(configurationBody)
        } else null
    }

    override fun putImageBaseUrlExpirationTime(expirationTime: Long) {
        edit { putLong(KEY_IMG_BASE_URL_EXPIRATION_TIME, expirationTime) }
    }

    override fun getImageBaseUrlExpirationTime(): Long {
        return preferences.getLong(KEY_IMG_BASE_URL_EXPIRATION_TIME, -1)
    }

    override fun observeTheme(): Flow<Theme> {
        return preferenceKeyChangedFlow
            // Emit on start so that we always send the initial value
            .onStart { emit(KEY_THEME) }
            .filter { it == KEY_THEME }
            .map { theme }
            .distinctUntilChanged()
    }

    private fun getThemeForStorageKey(value: String) =
        when (value) {
            Theme.DARK.value -> {
                Theme.DARK
            }
            else -> {
                Theme.LIGHT
            }
        }

    private inline fun edit(block: SharedPreferences.Editor.() -> Unit) {
        with(preferences.edit()) {
            block()
            commit()
        }
    }
}