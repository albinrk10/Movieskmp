package io.albin.kmpmovies.domain.usecases

import io.albin.kmpmovies.domain.entities.Movie
import io.albin.kmpmovies.domain.repository.MoviesRepository

class SearchMoviesUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(query: String): List<Movie> {
        val trimmed = query.trim()
        if (trimmed.isBlank()) return emptyList()
        return repository.searchMovies(trimmed)
    }
}
