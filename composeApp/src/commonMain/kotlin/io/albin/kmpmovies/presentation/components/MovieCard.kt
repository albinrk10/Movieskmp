package io.albin.kmpmovies.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import io.albin.kmpmovies.core.designsystem.AppTheme
import io.albin.kmpmovies.domain.entities.Movie

@Composable
fun MovieCard(
    movie: Movie,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(AppTheme.shapes.posterCorner)
            .clickable(onClick = onClick)
    ) {
        Card(
            shape = AppTheme.shapes.posterCorner,
            colors = CardDefaults.cardColors(
                containerColor = AppTheme.colors.cardSurface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 3f)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = movie.poster,
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                RatingBadge(
                    voteAverage = movie.voteAverage,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(AppTheme.spacing.s)
                )
            }
        }

        Spacer(modifier = Modifier.height(AppTheme.spacing.s))

        Text(
            text = movie.title,
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.SemiBold,
                lineHeight = 18.sp
            ),
            color = AppTheme.colors.textPrimary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        val year = movie.releaseDate.take(4)
        if (year.isNotEmpty()) {
            Spacer(modifier = Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = year,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.colors.textMuted
                )
                if (movie.originalLanguage.isNotEmpty()) {
                    Text(
                        text = " • ${movie.originalLanguage.uppercase()}",
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.colors.textMuted
                    )
                }
            }
        }
    }
}
