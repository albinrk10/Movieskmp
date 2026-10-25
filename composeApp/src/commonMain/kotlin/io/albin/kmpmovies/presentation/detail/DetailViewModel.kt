package io.albin.kmpmovies.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.albin.kmpmovies.domain.entities.Movie
import io.albin.kmpmovies.domain.usecases.GetMovieDetailUseCase
import io.albin.kmpmovies.domain.usecases.GetMovieTrailerUseCase
import io.albin.kmpmovies.domain.usecases.IsFavoriteUseCase
import io.albin.kmpmovies.domain.usecases.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val id: Int,
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieTrailerUseCase: GetMovieTrailerUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        loadMovieDetails(id)
        observeFavoriteStatus(id)
    }

    fun loadMovieDetails(movieId: Int = id) {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, error = null) }
            try {
                val movie = getMovieDetailUseCase(movieId)
                val trailerKey = getMovieTrailerUseCase(movieId)
                _uiState.update {
                    it.copy(
                        loading = false,
                        movie = movie,
                        trailerKey = trailerKey
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        loading = false,
                        error = e.message ?: "Error al cargar los detalles de la película"
                    )
                }
            }
        }
    }

    private fun observeFavoriteStatus(movieId: Int) {
        viewModelScope.launch {
            isFavoriteUseCase(movieId).collectLatest { isFav ->
                _uiState.update { it.copy(isFavorite = isFav) }
            }
        }
    }

    fun toggleFavorite() {
        val movie = _uiState.value.movie ?: return
        viewModelScope.launch {
            toggleFavoriteUseCase(movie)
        }
    }
}
