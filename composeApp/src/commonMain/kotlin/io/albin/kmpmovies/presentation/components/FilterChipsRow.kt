package io.albin.kmpmovies.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.albin.kmpmovies.core.designsystem.AppTheme
import io.albin.kmpmovies.presentation.viewmodel.home.MovieFilter

@Composable
fun FilterChipsRow(
    selectedFilter: MovieFilter,
    onFilterSelected: (MovieFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = AppTheme.spacing.m, vertical = AppTheme.spacing.s),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.s)
    ) {
        MovieFilter.entries.forEach { filter ->
            val isSelected = filter == selectedFilter
            FilterChip(
                selected = isSelected,
                onClick = { onFilterSelected(filter) },
                label = {
                    Text(
                        text = filter.label,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 13.sp
                    )
                },
                shape = AppTheme.shapes.chipCorner,
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = AppTheme.colors.surfaceVariant,
                    labelColor = AppTheme.colors.textMuted,
                    selectedContainerColor = AppTheme.colors.actionYellow,
                    selectedLabelColor = Color.Black
                ),
                border = null
            )
        }
    }
}
