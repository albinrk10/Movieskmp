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

    override suspend fun searchMovies(query: String): List<Movie> {
        return remoteDataSource.searchMovies(query).results.map { it.toDomain() }
    }

    override suspend fun getMovieTrailerKey(movieId: Int): String? {
        val videos = remoteDataSource.fetchMovieVideos(movieId).results
        val youtubeVideos = videos.filter { it.site.equals("YouTube", ignoreCase = true) }
        val officialTrailer = youtubeVideos.firstOrNull {
            it.type.equals("Trailer", ignoreCase = true) && it.official
        }
        val anyTrailer = youtubeVideos.firstOrNull {
            it.type.equals("Trailer", ignoreCase = true)
        }
        val teaser = youtubeVideos.firstOrNull {
            it.type.equals("Teaser", ignoreCase = true)
        }
        return (officialTrailer ?: anyTrailer ?: teaser ?: youtubeVideos.firstOrNull())?.key
    }
}
