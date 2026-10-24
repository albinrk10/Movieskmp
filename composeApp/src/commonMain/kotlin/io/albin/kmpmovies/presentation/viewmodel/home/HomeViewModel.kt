package io.albin.kmpmovies.presentation.viewmodel.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.albin.kmpmovies.domain.entities.Movie
import io.albin.kmpmovies.domain.usecases.GetPopularMoviesUseCase
import kotlinx.coroutines.launch

enum class MovieFilter(val label: String) {
    ALL("Todos"),
    POPULAR("Populares"),
    TOP_RATED("Mejor valoradas"),
    LATEST("Estrenos")
}

class HomeViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            state = state.copy(loading = true, error = null)
            try {
                val movies = getPopularMoviesUseCase()
                val featured = movies.maxByOrNull { it.popularity } ?: movies.firstOrNull()
                state = state.copy(
                    loading = false,
                    rawMovies = movies,
                    filteredMovies = applyFilter(movies, state.selectedFilter),
                    featuredMovie = featured
                )
            } catch (e: Exception) {
                state = state.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    fun onFilterSelected(filter: MovieFilter) {
        state = state.copy(
            selectedFilter = filter,
            filteredMovies = applyFilter(state.rawMovies, filter)
        )
    }

    private fun applyFilter(movies: List<Movie>, filter: MovieFilter): List<Movie> {
        return when (filter) {
            MovieFilter.ALL, MovieFilter.POPULAR -> movies
            MovieFilter.TOP_RATED -> movies.sortedByDescending { it.voteAverage }
            MovieFilter.LATEST -> movies.sortedByDescending { it.releaseDate }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val rawMovies: List<Movie> = emptyList(),
        val filteredMovies: List<Movie> = emptyList(),
        val featuredMovie: Movie? = null,
        val selectedFilter: MovieFilter = MovieFilter.ALL,
        val error: String? = null
    ) {
        val movies: List<Movie> get() = filteredMovies
    }
}
