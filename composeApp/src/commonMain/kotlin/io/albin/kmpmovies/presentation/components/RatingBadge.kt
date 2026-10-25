package io.albin.kmpmovies.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.albin.kmpmovies.core.designsystem.AppTheme
import kotlin.math.round

@Composable
fun RatingBadge(
    voteAverage: Double,
    modifier: Modifier = Modifier
) {
    if (voteAverage <= 0.0) {
        Row(
            modifier = modifier
                .background(
                    color = AppTheme.colors.cardSurface.copy(alpha = 0.85f),
                    shape = AppTheme.shapes.pillCorner
                )
                .padding(horizontal = 6.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = null,
                tint = AppTheme.colors.actionYellow,
                modifier = Modifier.size(11.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = "Próximamente",
                color = AppTheme.colors.textPrimary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
    } else {
        val roundedScore = round(voteAverage * 10) / 10.0
        val scoreText = if (roundedScore % 1.0 == 0.0) {
            "${roundedScore.toInt()}.0"
        } else {
            "$roundedScore"
        }

        Row(
            modifier = modifier
                .background(
                    color = Color.Black.copy(alpha = 0.75f),
                    shape = AppTheme.shapes.pillCorner
                )
                .padding(horizontal = 6.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = AppTheme.colors.starAmber,
                modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = scoreText,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
