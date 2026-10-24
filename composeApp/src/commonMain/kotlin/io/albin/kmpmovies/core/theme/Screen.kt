package io.albin.kmpmovies.core.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Screen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CinematicColorScheme
    ) {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}
