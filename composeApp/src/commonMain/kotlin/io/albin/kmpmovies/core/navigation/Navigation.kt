package io.albin.kmpmovies.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.albin.kmpmovies.core.routes.Route
import io.albin.kmpmovies.presentation.detail.DetailScreen
import io.albin.kmpmovies.presentation.detail.DetailViewModel
import io.albin.kmpmovies.presentation.favorites.FavoritesScreen
import io.albin.kmpmovies.presentation.favorites.FavoritesViewModel
import io.albin.kmpmovies.presentation.home.HomeScreen
import io.albin.kmpmovies.presentation.home.HomeViewModel
import io.albin.kmpmovies.presentation.search.SearchScreen
import io.albin.kmpmovies.presentation.search.SearchViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Home.route
    ) {
        composable(Route.Home.route) {
            val vm = koinViewModel<HomeViewModel>()
            HomeScreen(
                vm = vm,
                onMovieClick = { movie ->
                    navController.navigate(Route.Detail.createRoute(movie.id))
                },
                onSearchClick = {
                    navController.navigate(Route.Search.route)
                },
                onFavoritesClick = {
                    navController.navigate(Route.Favorites.route)
                }
            )
        }

        composable(Route.Search.route) {
            val vm = koinViewModel<SearchViewModel>()
            SearchScreen(
                vm = vm,
                onMovieClick = { movie ->
                    navController.navigate(Route.Detail.createRoute(movie.id))
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Route.Favorites.route) {
            val vm = koinViewModel<FavoritesViewModel>()
            FavoritesScreen(
                vm = vm,
                onMovieClick = { movie ->
                    navController.navigate(Route.Detail.createRoute(movie.id))
                },
                onBack = { navController.popBackStack() }
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
            val vm = koinViewModel<DetailViewModel>(
                key = "detail_$movieId",
                parameters = { parametersOf(movieId) }
            )
            DetailScreen(
                vm = vm,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
