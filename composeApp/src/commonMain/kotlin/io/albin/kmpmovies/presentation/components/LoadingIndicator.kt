package io.albin.kmpmovies.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.albin.kmpmovies.core.designsystem.AppTheme

@Composable
fun LoadingIndicator(
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    if (enabled) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = AppTheme.colors.actionYellow)
        }
    }
}
