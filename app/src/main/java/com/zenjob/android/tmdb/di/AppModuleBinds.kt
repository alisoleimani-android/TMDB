package com.zenjob.android.tmdb.di

import com.zenjob.android.tmdb.appinitializers.Initializer
import com.zenjob.android.tmdb.appinitializers.PreferencesInitializer
import com.zenjob.android.tmdb.appinitializers.TimberInitializer
import com.zenjob.android.tmdb.common.data.api.ConnectionManager
import com.zenjob.android.tmdb.common.data.api.ConnectionManagerImpl
import com.zenjob.android.tmdb.common.utils.CoroutineDispatchersProvider
import com.zenjob.android.tmdb.common.utils.DispatchersProvider
import com.zenjob.android.tmdb.common.utils.Logger
import com.zenjob.android.tmdb.common.utils.TmdbLogger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Suppress("unused")
@InstallIn(SingletonComponent::class)
@Module
abstract class AppModuleBinds {

    @Singleton
    @Binds
    abstract fun provideLogger(bind: TmdbLogger): Logger

    @Binds
    @IntoSet
    abstract fun provideTimberInitializer(bind: TimberInitializer): Initializer

    @Binds
    @IntoSet
    abstract fun providePreferencesInitializer(bind: PreferencesInitializer): Initializer

    @Binds
    abstract fun bindDispatchersProvider(provider: CoroutineDispatchersProvider): DispatchersProvider

    @Binds
    abstract fun bindConnectionManager(manager: ConnectionManagerImpl): ConnectionManager

}
