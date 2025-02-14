package io.albin.kmpmovies.ui.Screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.albin.kmpmovies.data.Movie
import io.albin.kmpmovies.data.MoviesRepository
import io.albin.kmpmovies.data.RemoteMovie
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository : MoviesRepository
): ViewModel(

) {
    var state by mutableStateOf(UiState())

    private set

    init {
        viewModelScope.launch {
            state =UiState(loading = true)
            state= UiState(
            loading = false,
              movies = repository.fetchPopularMovies()
            )

        }
    }
  data class UiState(
      val loading: Boolean = false,
      val movies: List<Movie> = emptyList()
  )
}

private fun RemoteMovie.toDomainMovie() = Movie(
    id=  id,
    title=  title,
    overview= overview,
    releaseDate= releaseDate,
    poster = "https://image.tmdb.org/t/p/w185/$posterPath",
    backdrop= backdropPath?.let { "https://image.tmdb.org/t/p/w780/$it" },
    originalLanguage= originalLanguage,
    originalTitle=  originalTitle,
    popularity=popularity,
    voteAverage= voteAverage,
)