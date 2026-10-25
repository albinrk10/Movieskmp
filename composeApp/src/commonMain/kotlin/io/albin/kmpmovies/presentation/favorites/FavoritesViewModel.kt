package io.albin.kmpmovies.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.albin.kmpmovies.domain.entities.Movie
import io.albin.kmpmovies.domain.usecases.GetFavoritesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState(loading = true))
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    val favorites: StateFlow<List<Movie>> = getFavoritesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, error = null) }
            getFavoritesUseCase()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            loading = false,
                            error = e.message ?: "Error al cargar favoritos"
                        )
                    }
                }
                .collect { list ->
                    _uiState.update {
                        it.copy(
                            loading = false,
                            favorites = list,
                            error = null
                        )
                    }
                }
        }
    }
}
