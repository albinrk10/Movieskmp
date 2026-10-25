package io.albin.kmpmovies.domain.usecases

import io.albin.kmpmovies.domain.entities.Movie
import io.albin.kmpmovies.domain.repository.FavoriteRepository

class ToggleFavoriteUseCase(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(movie: Movie) {
        repository.toggleFavorite(movie)
    }
}
