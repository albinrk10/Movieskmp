package io.albin.kmpmovies.di

import io.albin.kmpmovies.BuildKonfig
import io.albin.kmpmovies.core.constants.Constants
import io.albin.kmpmovies.domain.repository.FavoriteRepository
import io.albin.kmpmovies.domain.repository.MoviesRepository
import io.albin.kmpmovies.infrastructure.datasources.FavoriteMovieDao
import io.albin.kmpmovies.infrastructure.datasources.KtorMoviesRemoteDataSource
import io.albin.kmpmovies.infrastructure.datasources.MoviesRemoteDataSource
import io.albin.kmpmovies.infrastructure.datasources.database.AppDatabase
import io.albin.kmpmovies.infrastructure.datasources.database.getRoomDatabase
import io.albin.kmpmovies.infrastructure.repositories.FavoriteRepositoryImpl
import io.albin.kmpmovies.infrastructure.repositories.MoviesRepositoryImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.http.append
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val dataModule = module {
    single {
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
                    parameters.append("api_key", BuildKonfig.TMDB_API_KEY)
                    parameters.append("language", "es-ES")
                }
            }
        }
    }

    single<MoviesRemoteDataSource> { KtorMoviesRemoteDataSource(get()) }
    single<AppDatabase> { getRoomDatabase() }
    single<FavoriteMovieDao> { get<AppDatabase>().favoriteMovieDao() }
    single<MoviesRepository> { MoviesRepositoryImpl(get()) }
    single<FavoriteRepository> { FavoriteRepositoryImpl(get()) }
}
