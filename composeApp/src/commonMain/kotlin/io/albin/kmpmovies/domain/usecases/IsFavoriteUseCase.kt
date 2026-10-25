package io.albin.kmpmovies.domain.usecases

import io.albin.kmpmovies.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class IsFavoriteUseCase(
    private val repository: FavoriteRepository
) {
    operator fun invoke(movieId: Int): Flow<Boolean> {
        return repository.isFavorite(movieId)
    }
}
