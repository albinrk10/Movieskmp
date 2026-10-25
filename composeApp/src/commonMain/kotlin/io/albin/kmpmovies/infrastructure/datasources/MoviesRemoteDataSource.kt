package io.albin.kmpmovies.infrastructure.datasources

import io.albin.kmpmovies.infrastructure.models.RemoteMovie
import io.albin.kmpmovies.infrastructure.models.RemoteResult
import io.albin.kmpmovies.infrastructure.models.VideosResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

interface MoviesRemoteDataSource {
    suspend fun fetchPopularMovies(): RemoteResult
    suspend fun fetchMovieById(id: Int): RemoteMovie
    suspend fun searchMovies(query: String): RemoteResult
    suspend fun fetchMovieVideos(movieId: Int): VideosResult
}

class KtorMoviesRemoteDataSource(
    private val client: HttpClient
) : MoviesRemoteDataSource {

    override suspend fun fetchPopularMovies(): RemoteResult {
        return client
            .get("/3/discover/movie?sort_by=popularity.desc")
            .body()
    }

    override suspend fun fetchMovieById(id: Int): RemoteMovie {
        return client
            .get("/3/movie/$id")
            .body()
    }

    override suspend fun searchMovies(query: String): RemoteResult {
        return client
            .get("/3/search/movie") {
                parameter("query", query)
            }
            .body()
    }

    override suspend fun fetchMovieVideos(movieId: Int): VideosResult {
        return client
            .get("/3/movie/$movieId/videos")
            .body()
    }
}
