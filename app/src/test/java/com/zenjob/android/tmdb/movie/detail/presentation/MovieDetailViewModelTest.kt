package com.zenjob.android.tmdb.movie.detail.presentation

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.zenjob.android.tmdb.common.domain.model.DETAIL_POSTER_SIZE
import com.zenjob.android.tmdb.common.domain.usecases.GetImageConfiguration
import com.zenjob.android.tmdb.common.presentation.ImageBaseUrlConfigurator
import com.zenjob.android.tmdb.common.presentation.model.mappers.UiConfigurationMapper
import com.zenjob.android.tmdb.common.presentation.model.mappers.UiImagesMapper
import com.zenjob.android.tmdb.common.presentation.model.mappers.UiMovieMapper
import com.zenjob.android.tmdb.movie.detail.domain.GetMovieDetail
import com.zenjob.android.tmdb.util.CoroutineRule
import com.zenjob.android.tmdb.util.configuration
import com.zenjob.android.tmdb.util.movie1
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MovieDetailViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    // Mock use cases
    private val mockGetMovieDetail: GetMovieDetail = mock()
    private val mockGetImageConfiguration: GetImageConfiguration = mock()

    private val imageBaseUrl: String
        get() {
            val posterSize = configuration.images.posterSizes.find { it == DETAIL_POSTER_SIZE }
            return configuration.images.baseUrl + posterSize
        }

    private val uiMovieMapper = UiMovieMapper()
    private val uiConfigurationMapper = UiConfigurationMapper(UiImagesMapper())

    @Test
    fun `Creating the ViewModel loads detail of the passed movie`() = runTest {
        val expectedMovie = movie1

        // Arrange
        whenever(mockGetImageConfiguration.invoke()).doReturn(Result.success(configuration))
        whenever(mockGetMovieDetail.invoke(expectedMovie.id)).doReturn(Result.success(expectedMovie))

        val viewModel = MovieDetailViewModel(
            getMovieDetail = mockGetMovieDetail,
            urlConfigurator = ImageBaseUrlConfigurator(mockGetImageConfiguration, uiConfigurationMapper),
            mapper = uiMovieMapper,
            savedStateHandle = SavedStateHandle(mapOf("movieId" to expectedMovie.id))
        )

        val expectedState =
            MovieDetailUiState(movie = uiMovieMapper.mapToView(expectedMovie, imageBaseUrl))

        // runs fetchMovieDetail() inside of init block
        runCurrent()

        val actualState = viewModel.uiState.value
        assertThat(actualState).isEqualTo(expectedState)
    }

}