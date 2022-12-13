package com.zenjob.android.tmdb.movie.popular.presentation

import com.zenjob.android.tmdb.common.data.TmdbMovieRepository
import com.zenjob.android.tmdb.common.data.api.ConnectionManager
import com.zenjob.android.tmdb.common.data.api.NetworkCallHandler
import com.zenjob.android.tmdb.common.data.api.TMDBApi
import com.zenjob.android.tmdb.common.data.api.model.mappers.ApiMovieMapper
import com.zenjob.android.tmdb.common.domain.usecases.GetImageConfiguration
import com.zenjob.android.tmdb.common.presentation.ImageBaseUrlConfigurator
import com.zenjob.android.tmdb.common.presentation.model.mappers.UiConfigurationMapper
import com.zenjob.android.tmdb.common.presentation.model.mappers.UiImagesMapper
import com.zenjob.android.tmdb.common.presentation.model.mappers.UiMovieMapper
import com.zenjob.android.tmdb.common.utils.DispatchersProvider
import com.zenjob.android.tmdb.common.utils.Logger
import com.zenjob.android.tmdb.movie.popular.domain.RequestNextPageOfPopularMoviesImpl
import com.zenjob.android.tmdb.util.CoroutineRule
import com.zenjob.android.tmdb.util.configuration
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*

@ExperimentalCoroutinesApi
class PopularMoviesViewModelIntegrationTest {

    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)

    @get:Rule
    val coroutineRule = CoroutineRule(testDispatcher)

    // Mock properties
    private val mockTmdbApi: TMDBApi = mock()
    private val mockLogger: Logger = mock()
    private val mockConnectionManager: ConnectionManager = mock()
    private val mockGetImageConfiguration: GetImageConfiguration = mock()

    private val uiMovieMapper = UiMovieMapper()
    private val uiConfigurationMapper = UiConfigurationMapper(UiImagesMapper())

    private lateinit var movieRepository: TmdbMovieRepository

    private lateinit var requestNextPageOfPopularMoviesImpl: RequestNextPageOfPopularMoviesImpl

    private val testDispatchersProvider = object : DispatchersProvider {
        override fun io(): CoroutineDispatcher = testDispatcher
    }

    @Before
    fun setUp() {
        movieRepository = TmdbMovieRepository(
            mockTmdbApi,
            ApiMovieMapper(),
            NetworkCallHandler(mockConnectionManager, testDispatchersProvider, mockLogger)
        )

        requestNextPageOfPopularMoviesImpl = RequestNextPageOfPopularMoviesImpl(movieRepository)
    }

    @Test
    fun `Calling loadNextItems triggers API`() = runTest {
        // Arrange
        whenever(mockConnectionManager.isConnected()).doReturn(true)
        whenever(mockGetImageConfiguration.invoke()).doReturn(Result.success(configuration))

        PopularMoviesViewModel(
            requestNextPageOfPopularMovies = requestNextPageOfPopularMoviesImpl,
            urlConfigurator = ImageBaseUrlConfigurator(
                mockGetImageConfiguration,
                uiConfigurationMapper
            ),
            mapper = uiMovieMapper
        )

        // Act
        runCurrent()

        // Assert
        verify(mockTmdbApi, times(1)).getPopularTvShows(page = 1)
    }
}