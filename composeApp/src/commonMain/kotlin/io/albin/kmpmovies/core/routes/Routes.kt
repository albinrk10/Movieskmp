package io.albin.kmpmovies.core.routes

sealed class Route(val route: String) {
    data object Home : Route("home")
    data object Search : Route("search")
    data object Favorites : Route("favorites")
    data object Detail : Route("details/{movieId}") {
        fun createRoute(movieId: Int): String = "details/$movieId"
    }
}
