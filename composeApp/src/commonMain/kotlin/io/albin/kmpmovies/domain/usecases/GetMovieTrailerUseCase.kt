package io.albin.kmpmovies.domain.usecases

import io.albin.kmpmovies.domain.repository.MoviesRepository

class GetMovieTrailerUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int): String? {
        return repository.getMovieTrailerKey(movieId)
    }
}
