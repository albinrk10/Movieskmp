package io.albin.kmpmovies.presentation.detail

import io.albin.kmpmovies.domain.entities.Movie

data class DetailUiState(
    val loading: Boolean = false,
    val movie: Movie? = null,
    val trailerKey: String? = null,
    val isFavorite: Boolean = false,
    val error: String? = null
)
