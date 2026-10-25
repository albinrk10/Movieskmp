package io.albin.kmpmovies.fakes

import io.albin.kmpmovies.domain.entities.Movie
import io.albin.kmpmovies.domain.repository.MoviesRepository

class FakeMoviesRepository : MoviesRepository {

    var popularMovies: List<Movie> = emptyList()
    var searchResults: List<Movie> = emptyList()
    var trailerKeyToReturn: String? = "dQw4w9WgXcQ"
    var shouldThrowError: Boolean = false

    override suspend fun getPopularMovies(): List<Movie> {
        if (shouldThrowError) throw RuntimeException("Error de red simulado")
        return popularMovies
    }

    override suspend fun getMovieById(id: Int): Movie {
        if (shouldThrowError) throw RuntimeException("Película no encontrada")
        return popularMovies.find { it.id == id } ?: throw NoSuchElementException("Movie $id not found")
    }

    override suspend fun searchMovies(query: String): List<Movie> {
        if (shouldThrowError) throw RuntimeException("Error en búsqueda simulada")
        return if (query.isBlank()) emptyList() else searchResults.filter {
            it.title.contains(query, ignoreCase = true)
        }
    }

    override suspend fun getMovieTrailerKey(movieId: Int): String? {
        return trailerKeyToReturn
    }
}
