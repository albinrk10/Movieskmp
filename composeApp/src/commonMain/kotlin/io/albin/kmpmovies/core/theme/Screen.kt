package io.albin.kmpmovies.core.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.albin.kmpmovies.core.designsystem.AppTheme

@Composable
fun Screen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    AppTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = AppTheme.colors.backgroundDark
        ) {
            content()
        }
    }
}
