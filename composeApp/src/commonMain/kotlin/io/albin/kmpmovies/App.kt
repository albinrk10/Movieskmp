package io.albin.kmpmovies

import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import io.albin.kmpmovies.core.designsystem.AppTheme
import io.albin.kmpmovies.core.navigation.Navigation
import io.albin.kmpmovies.di.dataModule
import io.albin.kmpmovies.di.domainModule
import io.albin.kmpmovies.di.viewModelModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .crossfade(true)
            .logger(DebugLogger())
            .build()
    }

    KoinApplication(
        application = {
            modules(dataModule, domainModule, viewModelModule)
        }
    ) {
        AppTheme {
            Navigation()
        }
    }
}
