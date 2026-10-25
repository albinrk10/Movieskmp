package io.albin.kmpmovies.di

import io.albin.kmpmovies.presentation.detail.DetailViewModel
import io.albin.kmpmovies.presentation.favorites.FavoritesViewModel
import io.albin.kmpmovies.presentation.home.HomeViewModel
import io.albin.kmpmovies.presentation.search.SearchViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::FavoritesViewModel)
    factory { (movieId: Int) ->
        DetailViewModel(
            id = movieId,
            getMovieDetailUseCase = get(),
            getMovieTrailerUseCase = get(),
            isFavoriteUseCase = get(),
            toggleFavoriteUseCase = get()
        )
    }
}
