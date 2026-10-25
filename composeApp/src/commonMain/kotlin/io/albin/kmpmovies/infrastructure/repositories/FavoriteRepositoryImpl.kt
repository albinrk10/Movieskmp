package io.albin.kmpmovies.infrastructure.repositories

import io.albin.kmpmovies.domain.entities.Movie
import io.albin.kmpmovies.domain.repository.FavoriteRepository
import io.albin.kmpmovies.infrastructure.datasources.FavoriteMovieDao
import io.albin.kmpmovies.infrastructure.models.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class FavoriteRepositoryImpl(
    private val favoriteMovieDao: FavoriteMovieDao
) : FavoriteRepository {

    override suspend fun toggleFavorite(movie: Movie) {
        val currentlyFavorite = favoriteMovieDao.isFavorite(movie.id).first()
        if (currentlyFavorite) {
            favoriteMovieDao.delete(movie.id)
        } else {
            favoriteMovieDao.insert(
                FavoriteMovieEntity(
                    id = movie.id,
                    title = movie.title,
                    poster = movie.poster,
                    backdrop = movie.backdrop,
                    releaseDate = movie.releaseDate,
                    voteAverage = movie.voteAverage,
                    overview = movie.overview,
                    originalTitle = movie.originalTitle,
                    originalLanguage = movie.originalLanguage,
                    popularity = movie.popularity,
                    createdAt = kotlin.time.TimeSource.Monotonic.markNow().elapsedNow().inWholeMilliseconds
                )
            )
        }
    }

    override fun isFavorite(movieId: Int): Flow<Boolean> {
        return favoriteMovieDao.isFavorite(movieId)
    }

    override fun getFavorites(): Flow<List<Movie>> {
        return favoriteMovieDao.getAll().map { list ->
            list.map { entity ->
                Movie(
                    id = entity.id,
                    title = entity.title,
                    overview = entity.overview,
                    releaseDate = entity.releaseDate,
                    poster = entity.poster,
                    backdrop = entity.backdrop,
                    originalTitle = entity.originalTitle,
                    originalLanguage = entity.originalLanguage,
                    popularity = entity.popularity,
                    voteAverage = entity.voteAverage
                )
            }
        }
    }
}
