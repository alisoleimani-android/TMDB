package com.zenjob.android.tmdb.movie.popular.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.zenjob.android.tmdb.common.domain.model.LIST_ITEM_POSTER_SIZE
import com.zenjob.android.tmdb.common.domain.usecases.GetImageConfiguration
import com.zenjob.android.tmdb.common.presentation.ImageBaseUrlConfigurator
import com.zenjob.android.tmdb.common.presentation.model.mappers.UiConfigurationMapper
import com.zenjob.android.tmdb.common.presentation.model.mappers.UiImagesMapper
import com.zenjob.android.tmdb.common.presentation.model.mappers.UiMovieMapper
import com.zenjob.android.tmdb.movie.popular.domain.RequestNextPageOfPopularMovies
import com.zenjob.android.tmdb.util.CoroutineRule
import com.zenjob.android.tmdb.util.configuration
import com.zenjob.android.tmdb.util.movie1
import com.zenjob.android.tmdb.util.movie2
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PopularMoviesViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    // Mock use cases
    private val mockRequestNextPageOfPopularMovies: RequestNextPageOfPopularMovies = mock()
    private val mockGetImageConfiguration: GetImageConfiguration = mock()

    private val imageBaseUrl: String
        get() {
            val posterSize = configuration.images.posterSizes.find { it == LIST_ITEM_POSTER_SIZE }
            return configuration.images.baseUrl + posterSize
        }

    private val uiMovieMapper = UiMovieMapper()
    private val uiConfigurationMapper = UiConfigurationMapper(UiImagesMapper())

    @Test
    fun `Creating the ViewModel loads the first page`() = runTest {
        // Arrange
        val firstPageItems = listOf(movie1, movie2)

        whenever(mockGetImageConfiguration.invoke()).doReturn(Result.success(configuration))

        whenever(
            mockRequestNextPageOfPopularMovies.invoke(page = 1)
        ).doReturn(Result.success(firstPageItems))

        val viewModel = PopularMoviesViewModel(
            requestNextPageOfPopularMovies = mockRequestNextPageOfPopularMovies,
            urlConfigurator = ImageBaseUrlConfigurator(mockGetImageConfiguration, uiConfigurationMapper),
            mapper = uiMovieMapper
        )
        val initialState = viewModel.uiState.value

        val uiItems = firstPageItems.map { uiMovieMapper.mapToView(it, imageBaseUrl) }

        val expectedUiState = initialState.copy(items = uiItems, page = 2)

        // Act
        runCurrent()

        // Assert
        val actualUiState = viewModel.uiState.value
        assertThat(actualUiState).isEqualTo(expectedUiState)
    }

    @Test
    fun `Calling onEvent(LoadNextItems) when ViewModel is created, loads next page`() = runTest {
        val firstPageItems = listOf(movie1)
        val secondPageItems = listOf(movie2)

        whenever(mockGetImageConfiguration.invoke()).doReturn(Result.success(configuration))

        whenever(
            mockRequestNextPageOfPopularMovies.invoke(page = 1)
        ).doReturn(Result.success(firstPageItems))

        whenever(
            mockRequestNextPageOfPopularMovies.invoke(page = 2)
        ).doReturn(Result.success(secondPageItems))

        val viewModel = PopularMoviesViewModel(
            requestNextPageOfPopularMovies = mockRequestNextPageOfPopularMovies,
            urlConfigurator = ImageBaseUrlConfigurator(mockGetImageConfiguration, uiConfigurationMapper),
            mapper = uiMovieMapper
        )
        val initialState = viewModel.uiState.value

        launch {
            viewModel.uiState.test {

                // Received the first page
                val firstState = awaitItem()
                assertThat(firstState).isEqualTo(
                    initialState.copy(
                        items = firstPageItems.map { uiMovieMapper.mapToView(it, imageBaseUrl) },
                        page = 2
                    )
                )

                // Loading second page
                val secondState = awaitItem()
                assertThat(secondState).isEqualTo(
                    firstState.copy(loadingNewItems = true)
                )

                // Received the second page
                val thirdState = awaitItem()
                assertThat(thirdState).isEqualTo(
                    secondState.copy(
                        items = secondState.items + secondPageItems.map {
                            uiMovieMapper.mapToView(it, imageBaseUrl)
                        },
                        page = 3,
                    )
                )

                cancelAndIgnoreRemainingEvents()
            }
        }

        // runs the initial loading
        runCurrent()

        // prepares and runs the second loading
        viewModel.onEvent(PopularMoviesFragmentEvent.LoadNextItems)
        runCurrent()
    }
}