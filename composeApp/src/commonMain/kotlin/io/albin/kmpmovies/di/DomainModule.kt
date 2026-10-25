package io.albin.kmpmovies.di

import io.albin.kmpmovies.domain.usecases.GetFavoritesUseCase
import io.albin.kmpmovies.domain.usecases.GetMovieDetailUseCase
import io.albin.kmpmovies.domain.usecases.GetMovieTrailerUseCase
import io.albin.kmpmovies.domain.usecases.GetPopularMoviesUseCase
import io.albin.kmpmovies.domain.usecases.IsFavoriteUseCase
import io.albin.kmpmovies.domain.usecases.SearchMoviesUseCase
import io.albin.kmpmovies.domain.usecases.ToggleFavoriteUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetPopularMoviesUseCase)
    factoryOf(::GetMovieDetailUseCase)
    factoryOf(::SearchMoviesUseCase)
    factoryOf(::GetMovieTrailerUseCase)
    factoryOf(::ToggleFavoriteUseCase)
    factoryOf(::IsFavoriteUseCase)
    factoryOf(::GetFavoritesUseCase)
}
