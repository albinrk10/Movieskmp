package io.albin.kmpmovies.core.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalSpacing = staticCompositionLocalOf { Spacing() }
val LocalAppColors = staticCompositionLocalOf { AppColors() }
val LocalAppShapes = staticCompositionLocalOf { AppShapes() }

object AppTheme {
    val spacing: Spacing
        @Composable
        @ReadOnlyComposable
        get() = LocalSpacing.current

    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val shapes: AppShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalAppShapes.current
}

@Composable
fun AppTheme(
    spacing: Spacing = AppTheme.spacing,
    colors: AppColors = AppTheme.colors,
    shapes: AppShapes = AppTheme.shapes,
    content: @Composable () -> Unit
) {
    val materialColorScheme = darkColorScheme(
        primary = colors.actionYellow,
        onPrimary = Color.Black,
        secondary = colors.actionYellow,
        background = colors.backgroundDark,
        onBackground = colors.textPrimary,
        surface = colors.cardSurface,
        onSurface = colors.textPrimary,
        surfaceVariant = colors.surfaceVariant,
        onSurfaceVariant = colors.textMuted,
        outline = colors.outline
    )

    CompositionLocalProvider(
        LocalSpacing provides spacing,
        LocalAppColors provides colors,
        LocalAppShapes provides shapes
    ) {
        MaterialTheme(
            colorScheme = materialColorScheme,
            content = content
        )
    }
}
