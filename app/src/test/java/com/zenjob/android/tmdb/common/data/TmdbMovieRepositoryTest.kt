package com.zenjob.android.tmdb.common.data

import com.google.common.truth.Truth.assertThat
import com.zenjob.android.tmdb.common.data.api.ConnectionManager
import com.zenjob.android.tmdb.common.data.api.NetworkCallHandler
import com.zenjob.android.tmdb.common.data.api.TMDBApi
import com.zenjob.android.tmdb.common.data.api.model.mappers.ApiMovieMapper
import com.zenjob.android.tmdb.common.domain.model.NetworkUnavailableException
import com.zenjob.android.tmdb.common.utils.DispatchersProvider
import com.zenjob.android.tmdb.common.utils.Logger
import com.zenjob.android.tmdb.util.CoroutineRule
import com.zenjob.android.tmdb.util.fakeResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class TmdbMovieRepositoryTest {

    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)

    @get:Rule
    val coroutineRule = CoroutineRule(testDispatcher)

    private val apiMovieMapper = ApiMovieMapper()

    private lateinit var repository: TmdbMovieRepository

    private val testDispatchersProvider = object : DispatchersProvider {
        override fun io(): CoroutineDispatcher = testDispatcher
    }

    // Mock properties
    private val mockTmdbApi: TMDBApi = mock()
    private val mockLogger: Logger = mock()
    private val mockConnectionManager: ConnectionManager = mock()

    @Before
    fun setUp() {
        repository = TmdbMovieRepository(
            mockTmdbApi,
            apiMovieMapper,
            NetworkCallHandler(mockConnectionManager, testDispatchersProvider, mockLogger)
        )
    }

    @Test
    fun `getPopularTvShows() returns a result`() = runTest {
        // Arrange
        whenever(mockConnectionManager.isConnected()).doReturn(true)
        whenever(mockTmdbApi.getPopularTvShows(page = 1)).doReturn(fakeResponse)

        // Act
        val result = repository.getPopularTvShows(page = 1)

        // Assert
        val actualDomainList = result.getOrNull()
        val expectedDomainList = fakeResponse.results.map { apiMovieMapper.mapToDomain(it) }
        assertThat(result.isSuccess).isTrue()
        assertThat(actualDomainList).isEqualTo(expectedDomainList)
    }

    @Test
    fun `getPopularTvShows() returns a throwable when user has NOT connection`() = runTest {
        // Arrange
        val expectedMessage = NetworkUnavailableException().message
        whenever(mockConnectionManager.isConnected()).doReturn(false)
        whenever(mockTmdbApi.getPopularTvShows(page = 1)).doReturn(fakeResponse)

        // Act
        val result = repository.getPopularTvShows(page = 1)

        // Assert
        val actualMessage = result.exceptionOrNull()?.message
        assertThat(result.isFailure).isTrue()
        assertThat(actualMessage).isEqualTo(expectedMessage)
    }
}