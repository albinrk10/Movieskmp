package io.albin.kmpmovies.unit

import app.cash.turbine.test
import io.albin.kmpmovies.domain.entities.Movie
import io.albin.kmpmovies.domain.usecases.SearchMoviesUseCase
import io.albin.kmpmovies.fakes.FakeMoviesRepository
import io.albin.kmpmovies.presentation.search.SearchUiState
import io.albin.kmpmovies.presentation.search.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeRepository: FakeMoviesRepository
    private lateinit var searchMoviesUseCase: SearchMoviesUseCase

    private val sampleMovie = Movie(
        id = 2,
        title = "Spider-Man",
        overview = "Peter Parker",
        releaseDate = "2026-07-29",
        poster = "/spiderman.jpg",
        backdrop = "/spiderman_bg.jpg",
        originalTitle = "Spider-Man",
        originalLanguage = "en",
        popularity = 120.0,
        voteAverage = 8.5
    )

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeMoviesRepository()
        fakeRepository.searchResults = listOf(sampleMovie)
        searchMoviesUseCase = SearchMoviesUseCase(fakeRepository)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial search state is Idle`() = runTest(testDispatcher) {
        val viewModel = SearchViewModel(searchMoviesUseCase)
        assertEquals(SearchUiState.Idle, viewModel.uiState.value)
        assertEquals("", viewModel.query.value)
    }

    @Test
    fun `when onQueryChanged is called with matching query debounce triggers Success`() = runTest(testDispatcher) {
        val viewModel = SearchViewModel(searchMoviesUseCase)

        viewModel.uiState.test {
            assertEquals(SearchUiState.Idle, awaitItem())

            viewModel.onQueryChanged("Spider")
            
            // Advance past debounce (400ms)
            advanceTimeBy(450)
            advanceUntilIdle()

            val successState = expectMostRecentItem()
            assertIs<SearchUiState.Success>(successState)
            assertEquals(1, successState.movies.size)
            assertEquals("Spider-Man", successState.movies.first().title)
        }
    }

    @Test
    fun `when onQueryChanged finds no matches emits Empty`() = runTest(testDispatcher) {
        val viewModel = SearchViewModel(searchMoviesUseCase)

        viewModel.uiState.test {
            assertEquals(SearchUiState.Idle, awaitItem())

            viewModel.onQueryChanged("NonExistentMovie123")
            advanceTimeBy(450)
            advanceUntilIdle()

            val emptyState = expectMostRecentItem()
            assertIs<SearchUiState.Empty>(emptyState)
        }
    }

    @Test
    fun `when clearQuery is called query is reset and state returns to Idle`() = runTest(testDispatcher) {
        val viewModel = SearchViewModel(searchMoviesUseCase)

        viewModel.onQueryChanged("Spider")
        advanceTimeBy(450)
        advanceUntilIdle()

        viewModel.clearQuery()

        assertEquals("", viewModel.query.value)
        assertEquals(SearchUiState.Idle, viewModel.uiState.value)
    }
}
