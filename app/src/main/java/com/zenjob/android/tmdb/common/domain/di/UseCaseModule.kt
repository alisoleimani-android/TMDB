package com.zenjob.android.tmdb.common.domain.di

import com.zenjob.android.tmdb.common.domain.usecases.GetImageConfiguration
import com.zenjob.android.tmdb.common.domain.usecases.GetImageConfigurationImpl
import com.zenjob.android.tmdb.movie.detail.domain.GetMovieDetail
import com.zenjob.android.tmdb.movie.detail.domain.GetMovieDetailImpl
import com.zenjob.android.tmdb.movie.popular.domain.RequestNextPageOfPopularMovies
import com.zenjob.android.tmdb.movie.popular.domain.RequestNextPageOfPopularMoviesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Suppress("unused")
@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindRequestNextPageOfPopularMovies(
        impl: RequestNextPageOfPopularMoviesImpl
    ): RequestNextPageOfPopularMovies

    @Binds
    abstract fun bindGetMovieDetail(impl: GetMovieDetailImpl): GetMovieDetail

    @Binds
    abstract fun bindGetImageConfiguration(impl: GetImageConfigurationImpl): GetImageConfiguration
}