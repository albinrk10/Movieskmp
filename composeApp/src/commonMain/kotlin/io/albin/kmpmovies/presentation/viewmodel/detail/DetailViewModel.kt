package io.albin.kmpmovies.presentation.viewmodel.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.albin.kmpmovies.domain.entities.Movie
import io.albin.kmpmovies.domain.usecases.GetMovieDetailUseCase
import io.albin.kmpmovies.domain.usecases.GetMovieTrailerUseCase
import io.albin.kmpmovies.domain.usecases.IsFavoriteUseCase
import io.albin.kmpmovies.domain.usecases.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailViewModel(
    private val id: Int,
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieTrailerUseCase: GetMovieTrailerUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    init {
        loadMovieDetail()
        observeFavoriteStatus()
    }

    private fun loadMovieDetail() {
        viewModelScope.launch {
            state = state.copy(loading = true, error = null)
            try {
                val movie = getMovieDetailUseCase(id)
                val trailerKey = getMovieTrailerUseCase(id)
                state = state.copy(
                    loading = false,
                    movie = movie,
                    trailerKey = trailerKey
                )
            } catch (e: Exception) {
                state = state.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    private fun observeFavoriteStatus() {
        viewModelScope.launch {
            isFavoriteUseCase(id).collectLatest { isFav ->
                state = state.copy(isFavorite = isFav)
            }
        }
    }

    fun toggleFavorite() {
        val movie = state.movie ?: return
        viewModelScope.launch {
            toggleFavoriteUseCase(movie)
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movie: Movie? = null,
        val trailerKey: String? = null,
        val isFavorite: Boolean = false,
        val error: String? = null
    )
}
