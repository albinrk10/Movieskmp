package io.albin.kmpmovies.core.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

val CinematicDarkBackground = Color(0xFF0F1115)
val CinematicDarkSurface = Color(0xFF181B22)
val CinematicDarkSurfaceVariant = Color(0xFF222631)
val CinematicGold = Color(0xFFF5C518)
val CinematicStar = Color(0xFFFFC107)
val CinematicTextPrimary = Color(0xFFF8FAFC)
val CinematicTextSecondary = Color(0xFF94A3B8)
val CinematicRed = Color(0xFFE50914)
val CinematicCardBorder = Color(0xFF2A2E3D)

val CinematicColorScheme = darkColorScheme(
    primary = CinematicGold,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF332900),
    onPrimaryContainer = CinematicGold,
    secondary = Color(0xFF3880FF),
    onSecondary = Color.White,
    background = CinematicDarkBackground,
    onBackground = CinematicTextPrimary,
    surface = CinematicDarkSurface,
    onSurface = CinematicTextPrimary,
    surfaceVariant = CinematicDarkSurfaceVariant,
    onSurfaceVariant = CinematicTextSecondary,
    outline = CinematicCardBorder
)
