package io.albin.kmpmovies.domain.repository

import io.albin.kmpmovies.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun toggleFavorite(movie: Movie)
    fun isFavorite(movieId: Int): Flow<Boolean>
    fun getFavorites(): Flow<List<Movie>>
}
