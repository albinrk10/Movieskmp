package io.albin.kmpmovies.domain.usecases

import io.albin.kmpmovies.domain.entities.Movie
import io.albin.kmpmovies.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(
    private val repository: FavoriteRepository
) {
    operator fun invoke(): Flow<List<Movie>> {
        return repository.getFavorites()
    }
}
