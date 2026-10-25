package io.albin.kmpmovies.presentation.viewmodel.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.albin.kmpmovies.domain.entities.Movie
import io.albin.kmpmovies.domain.usecases.GetFavoritesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FavoritesViewModel(
    getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    val favorites: StateFlow<List<Movie>> = getFavoritesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
