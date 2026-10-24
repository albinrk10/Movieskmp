package io.albin.kmpmovies.infrastructure.repositories

import io.albin.kmpmovies.domain.entities.Movie
import io.albin.kmpmovies.domain.repository.MoviesRepository
import io.albin.kmpmovies.infrastructure.datasources.MoviesRemoteDataSource
import io.albin.kmpmovies.infrastructure.mappers.toDomain

class MoviesRepositoryImpl(
    private val remoteDataSource: MoviesRemoteDataSource
) : MoviesRepository {

    override suspend fun getPopularMovies(): List<Movie> {
        return remoteDataSource.fetchPopularMovies().results.map { it.toDomain() }
    }

    override suspend fun getMovieById(id: Int): Movie {
        return remoteDataSource.fetchMovieById(id).toDomain()
    }
}
