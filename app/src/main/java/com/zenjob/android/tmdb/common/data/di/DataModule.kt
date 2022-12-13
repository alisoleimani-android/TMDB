package com.zenjob.android.tmdb.common.data.di

import com.zenjob.android.tmdb.common.data.AppRepositoryImpl
import com.zenjob.android.tmdb.common.data.TmdbMovieRepository
import com.zenjob.android.tmdb.common.domain.repositories.AppRepository
import com.zenjob.android.tmdb.common.domain.repositories.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Suppress("unused")
@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class DataModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindTmdbMovieRepository(repository: TmdbMovieRepository): MovieRepository

    @Binds
    @ActivityRetainedScoped
    abstract fun bindAppRepository(repository: AppRepositoryImpl): AppRepository
}