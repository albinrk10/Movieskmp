package io.albin.kmpmovies.presentation.favorites

import io.albin.kmpmovies.domain.entities.Movie

data class FavoritesUiState(
    val loading: Boolean = false,
    val favorites: List<Movie> = emptyList(),
    val error: String? = null
)
