package io.albin.kmpmovies.presentation.search

import io.albin.kmpmovies.domain.entities.Movie

sealed interface SearchUiState {
    data object Idle : SearchUiState
    data object Loading : SearchUiState
    data class Success(val movies: List<Movie>) : SearchUiState
    data object Empty : SearchUiState
    data class Error(val message: String) : SearchUiState
}
