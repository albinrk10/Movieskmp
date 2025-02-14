package io.albin.kmpmovies.ui.Screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.albin.kmpmovies.data.Movie
import io.albin.kmpmovies.data.MoviesRepository
import kotlinx.coroutines.launch


class DetailViewModel (
 private val id: Int,
private val repository : MoviesRepository

):ViewModel()
{
    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(loading = true)
            state = UiState(
                loading = false,
                movie = repository.fetchMovieById(id)
            )
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movie: Movie? = null
    )

}