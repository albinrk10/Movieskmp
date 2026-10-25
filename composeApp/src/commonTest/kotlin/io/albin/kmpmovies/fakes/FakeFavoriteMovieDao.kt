package io.albin.kmpmovies.fakes

import io.albin.kmpmovies.infrastructure.datasources.FavoriteMovieDao
import io.albin.kmpmovies.infrastructure.models.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class FakeFavoriteMovieDao : FavoriteMovieDao {

    private val _movies = MutableStateFlow<Map<Int, FavoriteMovieEntity>>(emptyMap())

    override suspend fun insert(movie: FavoriteMovieEntity) {
        _movies.value = _movies.value + (movie.id to movie)
    }

    override suspend fun delete(id: Int) {
        _movies.value = _movies.value - id
    }

    override fun isFavorite(id: Int): Flow<Boolean> {
        return _movies.map { it.containsKey(id) }
    }

    override fun getAll(): Flow<List<FavoriteMovieEntity>> {
        return _movies.map { it.values.toList() }
    }
}
