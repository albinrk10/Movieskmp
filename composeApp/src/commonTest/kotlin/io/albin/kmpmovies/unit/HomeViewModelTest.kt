package io.albin.kmpmovies.unit

import app.cash.turbine.test
import io.albin.kmpmovies.domain.entities.Movie
import io.albin.kmpmovies.domain.usecases.GetPopularMoviesUseCase
import io.albin.kmpmovies.fakes.FakeMoviesRepository
import io.albin.kmpmovies.presentation.home.HomeViewModel
import io.albin.kmpmovies.presentation.home.MovieFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeRepository: FakeMoviesRepository
    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    private val sampleMovies = listOf(
        Movie(
            id = 1,
            title = "Avatar",
            overview = "Pandora",
            releaseDate = "2009-12-18",
            poster = "/avatar.jpg",
            backdrop = "/avatar_bg.jpg",
            originalTitle = "Avatar",
            originalLanguage = "en",
            popularity = 95.0,
            voteAverage = 7.9
        ),
        Movie(
            id = 2,
            title = "Spider-Man",
            overview = "Spider-Man in NY",
            releaseDate = "2026-07-29",
            poster = "/spiderman.jpg",
            backdrop = "/spiderman_bg.jpg",
            originalTitle = "Spider-Man",
            originalLanguage = "en",
            popularity = 120.0,
            voteAverage = 8.5
        )
    )

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeMoviesRepository()
        fakeRepository.popularMovies = sampleMovies
        getPopularMoviesUseCase = GetPopularMoviesUseCase(fakeRepository)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when HomeViewModel initializes, it loads movies and selects featured movie by popularity`() = runTest(testDispatcher) {
        val viewModel = HomeViewModel(getPopularMoviesUseCase)

        viewModel.uiState.test {
            // Initial emission
            val initial = awaitItem()
            
            // Advance coroutines to complete loadMovies()
            advanceUntilIdle()

            val loadedState = expectMostRecentItem()
            assertFalse(loadedState.loading)
            assertNull(loadedState.error)
            assertEquals(2, loadedState.movies.size)
            // Highest popularity (120.0) should be featured
            assertNotNull(loadedState.featuredMovie)
            assertEquals("Spider-Man", loadedState.featuredMovie?.title)
        }
    }

    @Test
    fun `when TOP_RATED filter is selected, movies are sorted by voteAverage descending`() = runTest(testDispatcher) {
        val viewModel = HomeViewModel(getPopularMoviesUseCase)
        advanceUntilIdle()

        viewModel.onFilterSelected(MovieFilter.TOP_RATED)

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(MovieFilter.TOP_RATED, state.selectedFilter)
            assertEquals("Spider-Man", state.filteredMovies.first().title) // 8.5 vs 7.9
            assertEquals("Avatar", state.filteredMovies.last().title)
        }
    }

    @Test
    fun `when loadMovies fails, uiState emits error message and loading false`() = runTest(testDispatcher) {
        fakeRepository.shouldThrowError = true
        val viewModel = HomeViewModel(getPopularMoviesUseCase)

        viewModel.uiState.test {
            advanceUntilIdle()
            val state = expectMostRecentItem()
            assertFalse(state.loading)
            assertNotNull(state.error)
            assertEquals("Error de red simulado", state.error)
        }
    }
}
