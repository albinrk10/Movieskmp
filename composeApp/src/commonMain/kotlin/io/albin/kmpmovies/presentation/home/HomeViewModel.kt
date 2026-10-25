package io.albin.kmpmovies.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.albin.kmpmovies.domain.entities.Movie
import io.albin.kmpmovies.domain.usecases.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, error = null) }
            try {
                val movies = getPopularMoviesUseCase()
                val featuredList = movies.sortedByDescending { it.popularity }.take(6)
                _uiState.update { current ->
                    current.copy(
                        loading = false,
                        rawMovies = movies,
                        filteredMovies = applyFilter(movies, current.selectedFilter),
                        featuredMovie = featuredList.firstOrNull(),
                        featuredMovies = featuredList
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        loading = false,
                        error = e.message ?: "Error al cargar películas"
                    )
                }
            }
        }
    }

    fun onFilterSelected(filter: MovieFilter) {
        _uiState.update { current ->
            current.copy(
                selectedFilter = filter,
                filteredMovies = applyFilter(current.rawMovies, filter)
            )
        }
    }

    private fun applyFilter(movies: List<Movie>, filter: MovieFilter): List<Movie> {
        return when (filter) {
            MovieFilter.ALL, MovieFilter.POPULAR -> movies
            MovieFilter.TOP_RATED -> movies.sortedByDescending { it.voteAverage }
            MovieFilter.LATEST -> movies.sortedByDescending { it.releaseDate }
        }
    }
}
