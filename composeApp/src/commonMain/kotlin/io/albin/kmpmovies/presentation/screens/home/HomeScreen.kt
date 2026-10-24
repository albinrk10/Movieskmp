package io.albin.kmpmovies.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.albin.kmpmovies.core.theme.CinematicDarkBackground
import io.albin.kmpmovies.core.theme.CinematicGold
import io.albin.kmpmovies.core.theme.Screen
import io.albin.kmpmovies.domain.entities.Movie
import io.albin.kmpmovies.presentation.components.FilterChipsRow
import io.albin.kmpmovies.presentation.components.HeroBanner
import io.albin.kmpmovies.presentation.components.LoadingIndicator
import io.albin.kmpmovies.presentation.components.MovieCard
import io.albin.kmpmovies.presentation.viewmodel.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMovieClick: (Movie) -> Unit,
    vm: HomeViewModel
) {
    Screen {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        val state = vm.state

        Scaffold(
            containerColor = CinematicDarkBackground,
            topBar = {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Movie,
                                contentDescription = null,
                                tint = CinematicGold,
                                modifier = Modifier.size(26.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Cinemapedia",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 0.5.sp
                                ),
                                color = Color.White
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Buscar",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = CinematicDarkBackground.copy(alpha = 0.95f)
                    ),
                    scrollBehavior = scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { padding ->
            LoadingIndicator(enabled = state.loading)

            if (!state.loading) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 150.dp),
                    contentPadding = PaddingValues(
                        start = 14.dp,
                        end = 14.dp,
                        top = padding.calculateTopPadding(),
                        bottom = 24.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    // 1. Hero / Featured Banner
                    state.featuredMovie?.let { featured ->
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            HeroBanner(
                                movie = featured,
                                onDetailsClick = onMovieClick
                            )
                        }
                    }

                    // 2. Filter category chips
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        FilterChipsRow(
                            selectedFilter = state.selectedFilter,
                            onFilterSelected = vm::onFilterSelected
                        )
                    }

                    // 3. Section Title
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Text(
                            text = state.selectedFilter.label,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }

                    // 4. Movies Grid
                    items(state.movies, key = { it.id }) { movie ->
                        MovieCard(
                            movie = movie,
                            onClick = { onMovieClick(movie) }
                        )
                    }
                }
            }
        }
    }
}
