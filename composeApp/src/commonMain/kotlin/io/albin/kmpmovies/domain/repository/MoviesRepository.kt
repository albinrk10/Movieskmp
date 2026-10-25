package io.albin.kmpmovies.domain.repository

import io.albin.kmpmovies.domain.entities.Movie

interface MoviesRepository {
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getMovieById(id: Int): Movie
    suspend fun searchMovies(query: String): List<Movie>
    suspend fun getMovieTrailerKey(movieId: Int): String?
}
