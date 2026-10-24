package io.albin.kmpmovies.presentation.viewmodel.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.albin.kmpmovies.domain.entities.Movie
import io.albin.kmpmovies.domain.usecases.GetPopularMoviesUseCase
import kotlinx.coroutines.launch

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
            state = UiState(loading = true)
            try {
                val movies = getPopularMoviesUseCase()
                state = UiState(
                    loading = false,
                    movies = movies
                )
            } catch (e: Exception) {
                state = UiState(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie> = emptyList(),
        val error: String? = null
    )
}
