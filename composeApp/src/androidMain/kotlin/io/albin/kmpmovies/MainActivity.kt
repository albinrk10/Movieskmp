package io.albin.kmpmovies

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        io.albin.kmpmovies.infrastructure.datasources.AndroidAppContext.init(applicationContext)
        enableEdgeToEdge()
        setContent {
            EnableTransparentStatusBar()
            App()
        }
    }

    @Composable
    private fun EnableTransparentStatusBar() {
        val view = LocalView.current
        val darkMode = isSystemInDarkTheme()
        if (!view.isInEditMode) {
            val window = (view.context as MainActivity).window
            window.statusBarColor = Color.TRANSPARENT
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                !darkMode
        }
    }
}

