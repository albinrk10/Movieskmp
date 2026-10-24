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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import io.albin.kmpmovies.core.theme.CinematicDarkBackground
import io.albin.kmpmovies.core.theme.CinematicDarkSurface
import io.albin.kmpmovies.core.theme.CinematicDarkSurfaceVariant
import io.albin.kmpmovies.core.theme.CinematicGold
import io.albin.kmpmovies.core.theme.CinematicRed
import io.albin.kmpmovies.core.theme.CinematicStar
import io.albin.kmpmovies.core.theme.CinematicTextSecondary
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
    var isFavorite by remember { mutableStateOf(false) }

    Screen {
        Scaffold(
            containerColor = CinematicDarkBackground
        ) { padding ->
            LoadingIndicator(enabled = state.loading)

            state.movie?.let { movie ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(bottom = 36.dp)
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
                                            CinematicDarkBackground.copy(alpha = 0.6f),
                                            CinematicDarkBackground
                                        )
                                    )
                                )
                        )

                        // Floating Top Bar with Glassmorphic action buttons
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .statusBarsPadding()
                                .padding(horizontal = 16.dp, vertical = 10.dp),
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
                                IconButton(onClick = { isFavorite = !isFavorite }) {
                                    Icon(
                                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = "Favorito",
                                        tint = if (isFavorite) CinematicRed else Color.White
                                    )
                                }
                            }
                        }

                        // Overlapping Poster and Main Title at the base of the backdrop
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                colors = CardDefaults.cardColors(containerColor = CinematicDarkSurface),
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
                                val score = round(movie.voteAverage * 10) / 10.0

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    if (year.isNotEmpty()) {
                                        Text(
                                            text = year,
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                                            color = CinematicTextSecondary
                                        )
                                        Text(
                                            text = "  •  ",
                                            color = CinematicTextSecondary
                                        )
                                    }

                                    Text(
                                        text = movie.originalLanguage.uppercase(),
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                        color = CinematicTextSecondary
                                    )

                                    Text(
                                        text = "  •  ",
                                        color = CinematicTextSecondary
                                    )

                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = CinematicStar,
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

                    Spacer(modifier = Modifier.height(16.dp))

                    // 2. Action Buttons (Primary "Ver Tráiler", Secondary "Favoritos" & "Compartir")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CinematicGold,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(12.dp),
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
                            onClick = {},
                            shape = RoundedCornerShape(12.dp),
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

                    Spacer(modifier = Modifier.height(16.dp))

                    // 3. Category Chips
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Película", "Estreno", "Cine").forEach { tag ->
                            AssistChip(
                                onClick = {},
                                label = {
                                    Text(
                                        text = tag,
                                        color = CinematicTextSecondary,
                                        fontSize = 12.sp
                                    )
                                },
                                shape = RoundedCornerShape(16.dp),
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = CinematicDarkSurfaceVariant
                                ),
                                border = null
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 4. Synopsis / Overview
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Sinopsis",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (movie.overview.isNotBlank()) movie.overview else "No hay descripción disponible para esta película.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                lineHeight = 23.sp
                            ),
                            color = Color(0xFFD1D5DB)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 5. Technical Metrics Grid
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
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

                            val score = round(movie.voteAverage * 10) / 10.0
                            MetricCard(
                                icon = Icons.Default.Star,
                                label = "Calificación",
                                value = "$score / 10",
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
