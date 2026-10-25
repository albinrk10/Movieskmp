package io.albin.kmpmovies.presentation.home

import io.albin.kmpmovies.domain.entities.Movie

enum class MovieFilter(val label: String) {
    ALL("Todos"),
    POPULAR("Populares"),
    TOP_RATED("Mejor valoradas"),
    LATEST("Estrenos")
}

data class HomeUiState(
    val loading: Boolean = false,
    val rawMovies: List<Movie> = emptyList(),
    val filteredMovies: List<Movie> = emptyList(),
    val featuredMovie: Movie? = null,
    val featuredMovies: List<Movie> = emptyList(),
    val selectedFilter: MovieFilter = MovieFilter.ALL,
    val error: String? = null
) {
    val movies: List<Movie> get() = filteredMovies
}
