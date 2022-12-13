package com.zenjob.android.tmdb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.zenjob.android.tmdb.common.data.preferences.Preferences
import com.zenjob.android.tmdb.common.domain.model.Theme
import com.zenjob.android.tmdb.common.utils.collectOn
import com.zenjob.android.tmdb.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeUpdates()
    }

    private fun observeUpdates() {
        preferences.observeTheme().collectOn(this) {
            AppCompatDelegate.setDefaultNightMode(
                when (it) {
                    Theme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
                    Theme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                }
            )
        }
    }
}