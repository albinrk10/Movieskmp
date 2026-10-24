package io.albin.kmpmovies.ui.Screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.albin.kmpmovies.data.MoviesRepository
import io.albin.kmpmovies.data.MoviesService
import io.albin.kmpmovies.ui.Screens.detail.DetailScreen
import io.albin.kmpmovies.ui.Screens.detail.DetailViewModel
import io.albin.kmpmovies.ui.Screens.home.HomeScreen
import io.albin.kmpmovies.ui.Screens.home.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.albin.kmpmovies.BuildKonfig
import io.ktor.http.URLProtocol
import io.ktor.http.append
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val repository = rememberMoviesRepository()
    val viewModel =
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(

                onMovieClick = { movie ->
                    navController.navigate("details/${movie.id}")
                },
                vm = viewModel { HomeViewModel(repository) }
            )
        }
        composable(
            route = "details/{movieId}",
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.IntType
                }
            )

        ) {
            backStackEntry ->
            val movieId = checkNotNull(backStackEntry.arguments?.getInt("movieId"))
            DetailScreen(
              vm = viewModel { DetailViewModel(movieId, repository) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun rememberMoviesRepository(
    apiKey: String = BuildKonfig.TMDB_API_KEY
): MoviesRepository = remember {
    val client =
        HttpClient() {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(DefaultRequest) {
                url {

                    protocol = URLProtocol.HTTPS
                    host = "api.themoviedb.org"
                    parameters.append("api_key", apiKey)
                }
            }
        }


    MoviesRepository(MoviesService(client))

}