package io.albin.kmpmovies.domain.usecases

import io.albin.kmpmovies.domain.entities.Movie
import io.albin.kmpmovies.domain.repository.MoviesRepository

class GetPopularMoviesUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(): List<Movie> {
        return repository.getPopularMovies()
    }
}
