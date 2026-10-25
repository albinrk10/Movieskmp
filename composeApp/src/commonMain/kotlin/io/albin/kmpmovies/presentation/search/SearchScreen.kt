package io.albin.kmpmovies.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.albin.kmpmovies.core.designsystem.AppTheme
import io.albin.kmpmovies.core.theme.Screen
import io.albin.kmpmovies.domain.entities.Movie
import io.albin.kmpmovies.presentation.components.LoadingIndicator
import io.albin.kmpmovies.presentation.components.MovieCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchScreen(
    onMovieClick: (Movie) -> Unit,
    onBack: () -> Unit,
    vm: SearchViewModel = koinViewModel()
) {
    val query by vm.query.collectAsState()
    val uiState by vm.uiState.collectAsState()

    Screen {
        Scaffold(
            containerColor = AppTheme.colors.backgroundDark,
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = AppTheme.spacing.s, vertical = AppTheme.spacing.xs),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }

                    TextField(
                        value = query,
                        onValueChange = vm::onQueryChanged,
                        placeholder = {
                            Text(
                                text = "Buscar películas...",
                                color = AppTheme.colors.textMuted
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = AppTheme.colors.actionYellow
                            )
                        },
                        trailingIcon = {
                            if (query.isNotEmpty()) {
                                IconButton(onClick = vm::clearQuery) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Limpiar",
                                        tint = Color.White
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(24.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = AppTheme.colors.surfaceVariant,
                            unfocusedContainerColor = AppTheme.colors.surfaceVariant,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = AppTheme.colors.actionYellow
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = AppTheme.spacing.s)
                    )
                }
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                when (val state = uiState) {
                    is SearchUiState.Idle -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(AppTheme.spacing.l),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Movie,
                                contentDescription = null,
                                tint = AppTheme.colors.textMuted.copy(alpha = 0.5f),
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.height(AppTheme.spacing.m))
                            Text(
                                text = "Encuentra tus películas favoritas",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = AppTheme.colors.textMuted
                            )
                            Spacer(modifier = Modifier.height(AppTheme.spacing.xs))
                            Text(
                                text = "Escribe el título en el buscador superior",
                                style = MaterialTheme.typography.bodySmall,
                                color = AppTheme.colors.textMuted.copy(alpha = 0.7f)
                            )
                        }
                    }

                    is SearchUiState.Loading -> {
                        LoadingIndicator(enabled = true)
                    }

                    is SearchUiState.Empty -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(AppTheme.spacing.l),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "No se encontraron películas para \"$query\"",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = AppTheme.colors.textMuted,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    is SearchUiState.Error -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(AppTheme.spacing.l),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = state.message,
                                style = MaterialTheme.typography.bodyMedium,
                                color = AppTheme.colors.favoriteRed,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    is SearchUiState.Success -> {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 150.dp),
                            contentPadding = PaddingValues(AppTheme.spacing.m),
                            horizontalArrangement = Arrangement.spacedBy(14.dp),
                            verticalArrangement = Arrangement.spacedBy(18.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
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
    }
}
