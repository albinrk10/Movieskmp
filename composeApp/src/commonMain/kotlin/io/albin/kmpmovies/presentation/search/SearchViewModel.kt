package io.albin.kmpmovies.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.albin.kmpmovies.domain.usecases.SearchMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchViewModel(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _query
                .debounce(400)
                .distinctUntilChanged()
                .collectLatest { q ->
                    val trimmed = q.trim()
                    if (trimmed.isBlank()) {
                        _uiState.value = SearchUiState.Idle
                    } else {
                        _uiState.value = SearchUiState.Loading
                        try {
                            val results = searchMoviesUseCase(trimmed)
                            if (results.isEmpty()) {
                                _uiState.value = SearchUiState.Empty
                            } else {
                                _uiState.value = SearchUiState.Success(results)
                            }
                        } catch (e: Exception) {
                            _uiState.value = SearchUiState.Error(e.message ?: "Error al buscar películas")
                        }
                    }
                }
        }
    }

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }

    fun onQueryChange(newQuery: String) = onQueryChanged(newQuery)

    fun clearQuery() {
        _query.value = ""
        _uiState.value = SearchUiState.Idle
    }
}
