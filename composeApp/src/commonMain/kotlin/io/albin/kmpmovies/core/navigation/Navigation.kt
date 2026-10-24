package io.albin.kmpmovies.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.albin.kmpmovies.core.routes.Route
import io.albin.kmpmovies.di.AppContainer
import io.albin.kmpmovies.presentation.screens.detail.DetailScreen
import io.albin.kmpmovies.presentation.screens.home.HomeScreen
import io.albin.kmpmovies.presentation.viewmodel.detail.DetailViewModel
import io.albin.kmpmovies.presentation.viewmodel.home.HomeViewModel

@Composable
fun Navigation(
    appContainer: AppContainer
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Home.route
    ) {
        composable(Route.Home.route) {
            HomeScreen(
                onMovieClick = { movie ->
                    navController.navigate(Route.Detail.createRoute(movie.id))
                },
                vm = viewModel {
                    HomeViewModel(appContainer.getPopularMoviesUseCase)
                }
            )
        }
        composable(
            route = Route.Detail.route,
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val movieId = checkNotNull(backStackEntry.arguments?.getInt("movieId"))
            DetailScreen(
                vm = viewModel {
                    DetailViewModel(movieId, appContainer.getMovieDetailUseCase)
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
