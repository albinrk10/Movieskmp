package io.albin.kmpmovies.presentation.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import io.albin.kmpmovies.core.designsystem.AppTheme
import io.albin.kmpmovies.domain.entities.Movie
import kotlinx.coroutines.delay

@Composable
fun HeroBannerCarousel(
    movies: List<Movie>,
    onDetailsClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    if (movies.isEmpty()) return

    val pagerState = rememberPagerState(pageCount = { movies.size })

    // Auto-scroll loop every 4.5 seconds
    LaunchedEffect(pagerState, movies.size) {
        while (true) {
            delay(4500)
            if (!pagerState.isScrollInProgress) {
                val nextPage = (pagerState.currentPage + 1) % movies.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(295.dp)
            .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val movie = movies[page]
            HeroBannerSlide(
                movie = movie,
                onDetailsClick = onDetailsClick
            )
        }

        // Carousel indicators (Dots) on bottom end
        if (movies.size > 1) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = AppTheme.spacing.m, bottom = 22.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(movies.size) { index ->
                    val isSelected = pagerState.currentPage == index
                    val dotWidth by animateDpAsState(targetValue = if (isSelected) 22.dp else 6.dp)
                    Box(
                        modifier = Modifier
                            .height(6.dp)
                            .width(dotWidth)
                            .background(
                                color = if (isSelected) AppTheme.colors.actionYellow else Color.White.copy(alpha = 0.45f),
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun HeroBanner(
    movie: Movie,
    onDetailsClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    HeroBannerCarousel(
        movies = listOf(movie),
        onDetailsClick = onDetailsClick,
        modifier = modifier
    )
}

@Composable
private fun HeroBannerSlide(
    movie: Movie,
    onDetailsClick: (Movie) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = movie.backdrop ?: movie.poster,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradient overlay for smooth cinematographic text contrast
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.4f),
                            Color.Transparent,
                            AppTheme.colors.backgroundDark.copy(alpha = 0.75f),
                            AppTheme.colors.backgroundDark
                        ),
                        startY = 0f
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(horizontal = AppTheme.spacing.m, vertical = 14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = AppTheme.colors.favoriteRed,
                    modifier = Modifier.padding(end = AppTheme.spacing.s)
                ) {
                    Text(
                        text = "DESTACADO",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                RatingBadge(voteAverage = movie.voteAverage)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = movie.title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-0.5).sp
                ),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { onDetailsClick(movie) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.actionYellow,
                    contentColor = Color.Black
                ),
                shape = AppTheme.shapes.buttonCorner,
                modifier = Modifier.height(38.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Ver detalles",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    }
}
