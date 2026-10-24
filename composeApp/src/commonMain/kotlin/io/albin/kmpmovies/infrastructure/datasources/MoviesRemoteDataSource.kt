package io.albin.kmpmovies.infrastructure.datasources

import io.albin.kmpmovies.infrastructure.models.RemoteMovie
import io.albin.kmpmovies.infrastructure.models.RemoteResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface MoviesRemoteDataSource {
    suspend fun fetchPopularMovies(): RemoteResult
    suspend fun fetchMovieById(id: Int): RemoteMovie
}

class KtorMoviesRemoteDataSource(
    private val client: HttpClient
) : MoviesRemoteDataSource {

    override suspend fun fetchPopularMovies(): RemoteResult {
        return client
            .get("/3/discover/movie?sort_by=popularity.desc")
            .body<RemoteResult>()
    }

    override suspend fun fetchMovieById(id: Int): RemoteMovie {
        return client
            .get("/3/movie/$id")
            .body<RemoteMovie>()
    }
}
