package io.albin.kmpmovies.presentation.viewmodel.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.albin.kmpmovies.domain.entities.Movie
import io.albin.kmpmovies.domain.usecases.GetMovieDetailUseCase
import kotlinx.coroutines.launch

class DetailViewModel(
    private val id: Int,
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    init {
        loadMovieDetail()
    }

    fun loadMovieDetail() {
        viewModelScope.launch {
            state = UiState(loading = true)
            try {
                val movie = getMovieDetailUseCase(id)
                state = UiState(
                    loading = false,
                    movie = movie
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
        val movie: Movie? = null,
        val error: String? = null
    )
}
