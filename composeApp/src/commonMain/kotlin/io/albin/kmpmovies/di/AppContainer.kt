package io.albin.kmpmovies.di

import io.albin.kmpmovies.BuildKonfig
import io.albin.kmpmovies.core.constants.Constants
import io.albin.kmpmovies.domain.repository.MoviesRepository
import io.albin.kmpmovies.domain.usecases.GetMovieDetailUseCase
import io.albin.kmpmovies.domain.usecases.GetPopularMoviesUseCase
import io.albin.kmpmovies.infrastructure.datasources.KtorMoviesRemoteDataSource
import io.albin.kmpmovies.infrastructure.datasources.MoviesRemoteDataSource
import io.albin.kmpmovies.infrastructure.repositories.MoviesRepositoryImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.http.append
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface AppContainer {
    val getPopularMoviesUseCase: GetPopularMoviesUseCase
    val getMovieDetailUseCase: GetMovieDetailUseCase
}

class DefaultAppContainer(
    apiKey: String = BuildKonfig.TMDB_API_KEY
) : AppContainer {

    private val httpClient: HttpClient by lazy {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                    host = Constants.TMDB_BASE_HOST
                    parameters.append("api_key", apiKey)
                }
            }
        }
    }

    private val remoteDataSource: MoviesRemoteDataSource by lazy {
        KtorMoviesRemoteDataSource(httpClient)
    }

    private val moviesRepository: MoviesRepository by lazy {
        MoviesRepositoryImpl(remoteDataSource)
    }

    override val getPopularMoviesUseCase: GetPopularMoviesUseCase by lazy {
        GetPopularMoviesUseCase(moviesRepository)
    }

    override val getMovieDetailUseCase: GetMovieDetailUseCase by lazy {
        GetMovieDetailUseCase(moviesRepository)
    }
}
