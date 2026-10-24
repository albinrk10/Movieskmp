package io.albin.kmpmovies.domain.usecases

import io.albin.kmpmovies.domain.entities.Movie
import io.albin.kmpmovies.domain.repository.MoviesRepository

class GetMovieDetailUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(id: Int): Movie {
        return repository.getMovieById(id)
    }
}
