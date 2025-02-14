package io.albin.kmpmovies


import androidx.compose.runtime.*
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import io.albin.kmpmovies.ui.Screens.Navigation
import io.albin.kmpmovies.ui.Screens.detail.DetailScreen
import io.albin.kmpmovies.ui.Screens.home.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview




@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App() {
    setSingletonImageLoaderFactory {
            context -> ImageLoader.Builder(context)
        .crossfade(true)
        .logger(DebugLogger())
        .build()
    }
   // HomeScreen()
  //  DetailScreen()
    Navigation()
    }

