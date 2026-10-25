package io.albin.kmpmovies.presentation.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import io.albin.kmpmovies.core.designsystem.AppTheme
import io.albin.kmpmovies.core.theme.Screen
import io.albin.kmpmovies.domain.entities.Movie
import io.albin.kmpmovies.presentation.components.LoadingIndicator
import io.albin.kmpmovies.presentation.components.MetricCard
import io.albin.kmpmovies.presentation.viewmodel.detail.DetailViewModel
import kotlin.math.round

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailScreen(
    vm: DetailViewModel,
    onBack: () -> Unit
) {
    val state = vm.state
    val uriHandler = LocalUriHandler.current

    Screen {
        Scaffold(
            containerColor = AppTheme.colors.backgroundDark
        ) { padding ->
            LoadingIndicator(enabled = state.loading)

            state.movie?.let { movie ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(bottom = AppTheme.spacing.xl)
                ) {
                    // 1. Immersive Header (Backdrop + Gradients + Floating Controls)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(340.dp)
                    ) {
                        AsyncImage(
                            model = movie.backdrop ?: movie.poster,
                            contentDescription = movie.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        // Vignette gradients: Top control contrast & bottom fade
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Black.copy(alpha = 0.65f),
                                            Color.Transparent,
                                            AppTheme.colors.backgroundDark.copy(alpha = 0.6f),
                                            AppTheme.colors.backgroundDark
                                        )
                                    )
                                )
                        )

                        // Floating Top Bar with Glassmorphic action buttons
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .statusBarsPadding()
                                .padding(horizontal = AppTheme.spacing.m, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = Color.Black.copy(alpha = 0.55f),
                                modifier = Modifier.size(42.dp)
                            ) {
                                IconButton(onClick = onBack) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                        contentDescription = "Volver",
                                        tint = Color.White
                                    )
                                }
                            }

                            Surface(
                                shape = CircleShape,
                                color = Color.Black.copy(alpha = 0.55f),
                                modifier = Modifier.size(42.dp)
                            ) {
                                IconButton(onClick = vm::toggleFavorite) {
                                    Icon(
                                        imageVector = if (state.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = "Favorito",
                                        tint = if (state.isFavorite) AppTheme.colors.favoriteRed else Color.White
                                    )
                                }
                            }
                        }

                        // Overlapping Poster and Main Title at the base of the backdrop
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .fillMaxWidth()
                                .padding(horizontal = AppTheme.spacing.m),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Card(
                                shape = AppTheme.shapes.posterCorner,
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                colors = CardDefaults.cardColors(containerColor = AppTheme.colors.cardSurface),
                                modifier = Modifier
                                    .width(110.dp)
                                    .height(165.dp)
                            ) {
                                AsyncImage(
                                    model = movie.poster,
                                    contentDescription = movie.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = movie.title,
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        lineHeight = 26.sp
                                    ),
                                    color = Color.White
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                // Inline Info Row: Year • Language • Rating
                                val year = movie.releaseDate.take(4)

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    if (year.isNotEmpty()) {
                                        Text(
                                            text = year,
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                                            color = AppTheme.colors.textMuted
                                        )
                                        Text(
                                            text = "  •  ",
                                            color = AppTheme.colors.textMuted
                                        )
                                    }

                                    Text(
                                        text = movie.originalLanguage.uppercase(),
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                        color = AppTheme.colors.textMuted
                                    )

                                    Text(
                                        text = "  •  ",
                                        color = AppTheme.colors.textMuted
                                    )

                                    if (movie.voteAverage <= 0.0) {
                                        Icon(
                                            imageVector = Icons.Default.CalendarToday,
                                            contentDescription = null,
                                            tint = AppTheme.colors.actionYellow,
                                            modifier = Modifier.size(13.dp)
                                        )
                                        Spacer(modifier = Modifier.width(3.dp))
                                        Text(
                                            text = "Próximamente",
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                            color = AppTheme.colors.actionYellow
                                        )
                                    } else {
                                        val score = round(movie.voteAverage * 10) / 10.0
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = AppTheme.colors.starAmber,
                                            modifier = Modifier.size(15.dp)
                                        )
                                        Spacer(modifier = Modifier.width(3.dp))
                                        Text(
                                            text = "$score / 10",
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(AppTheme.spacing.m))

                    // 2. Action Buttons (Shown ONLY if trailerKey != null)
                    state.trailerKey?.let { key ->
                        val youtubeUrl = "https://www.youtube.com/watch?v=$key"
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = AppTheme.spacing.m),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    uriHandler.openUri(youtubeUrl)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AppTheme.colors.actionYellow,
                                    contentColor = Color.Black
                                ),
                                shape = AppTheme.shapes.cardCorner,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(46.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Ver Tráiler",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }

                            OutlinedButton(
                                onClick = {
                                    uriHandler.openUri(youtubeUrl)
                                },
                                shape = AppTheme.shapes.cardCorner,
                                modifier = Modifier.height(46.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Compartir",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(AppTheme.spacing.m))
                    }

                    // 3. Category Chips
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppTheme.spacing.m),
                        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.s)
                    ) {
                        listOf("Película", "Estreno", "Cine").forEach { tag ->
                            AssistChip(
                                onClick = {},
                                label = {
                                    Text(
                                        text = tag,
                                        color = AppTheme.colors.textMuted,
                                        fontSize = 12.sp
                                    )
                                },
                                shape = AppTheme.shapes.chipCorner,
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = AppTheme.colors.surfaceVariant
                                ),
                                border = null
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(AppTheme.spacing.m))

                    // 4. Synopsis / Overview
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppTheme.spacing.m)
                    ) {
                        Text(
                            text = "Sinopsis",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(AppTheme.spacing.s))
                        Text(
                            text = if (movie.overview.isNotBlank()) movie.overview else "No hay descripción disponible para esta película.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                lineHeight = 23.sp
                            ),
                            color = Color(0xFFD1D5DB)
                        )
                    }

                    Spacer(modifier = Modifier.height(AppTheme.spacing.l))

                    // 5. Technical Metrics Grid
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppTheme.spacing.m)
                    ) {
                        Text(
                            text = "Información Técnica",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            MetricCard(
                                icon = Icons.AutoMirrored.Filled.TrendingUp,
                                label = "Popularidad",
                                value = "${movie.popularity.toInt()} pts",
                                modifier = Modifier.weight(1f)
                            )

                            val ratingValue = if (movie.voteAverage <= 0.0) {
                                "Próximamente"
                            } else {
                                val score = round(movie.voteAverage * 10) / 10.0
                                "$score / 10"
                            }

                            MetricCard(
                                icon = Icons.Default.Star,
                                label = "Calificación",
                                value = ratingValue,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            MetricCard(
                                icon = Icons.Default.CalendarToday,
                                label = "Fecha de estreno",
                                value = if (movie.releaseDate.isNotBlank()) movie.releaseDate else "Desconocida",
                                modifier = Modifier.weight(1f)
                            )

                            MetricCard(
                                icon = Icons.Default.Movie,
                                label = "Título original",
                                value = if (movie.originalTitle.isNotBlank()) movie.originalTitle else movie.title,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}
