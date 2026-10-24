# Movieskmp

Aplicación de películas para Android y iOS desarrollada con **Kotlin Multiplatform** y **Compose Multiplatform**. Consulta [The Movie Database (TMDB)](https://www.themoviedb.org/) para mostrar películas populares y sus detalles.

## Funcionalidades

- Catálogo de películas populares en una cuadrícula con póster y título.
- Pantalla de detalle con información de la película e imagen de portada.
- Navegación entre el catálogo y el detalle.

## Tecnologías

- Kotlin Multiplatform y Compose Multiplatform para compartir la interfaz y la lógica.
- Ktor para consultar la API de TMDB y Kotlinx Serialization para procesar las respuestas.
- Coil para cargar las imágenes y Navigation Compose para navegar entre pantallas.

## Estructura del proyecto (Clean Architecture)

El código compartido en `composeApp/src/commonMain` está organizado siguiendo **Clean Architecture**:

- `core/`: Configuración global, navegación ([Navigation.kt](file:///d:/Kmp/Movieskmp/composeApp/src/commonMain/kotlin/io/albin/kmpmovies/core/navigation/Navigation.kt)), rutas seguras ([Routes.kt](file:///d:/Kmp/Movieskmp/composeApp/src/commonMain/kotlin/io/albin/kmpmovies/core/routes/Routes.kt)), constantes ([Constants.kt](file:///d:/Kmp/Movieskmp/composeApp/src/commonMain/kotlin/io/albin/kmpmovies/core/constants/Constants.kt)) y temas.
- `domain/`: Modelos y entidades de negocio puros ([Movie.kt](file:///d:/Kmp/Movieskmp/composeApp/src/commonMain/kotlin/io/albin/kmpmovies/domain/entities/Movie.kt)), contratos de repositorios ([MoviesRepository.kt](file:///d:/Kmp/Movieskmp/composeApp/src/commonMain/kotlin/io/albin/kmpmovies/domain/repository/MoviesRepository.kt)) y casos de uso ([GetPopularMoviesUseCase.kt](file:///d:/Kmp/Movieskmp/composeApp/src/commonMain/kotlin/io/albin/kmpmovies/domain/usecases/GetPopularMoviesUseCase.kt), [GetMovieDetailUseCase.kt](file:///d:/Kmp/Movieskmp/composeApp/src/commonMain/kotlin/io/albin/kmpmovies/domain/usecases/GetMovieDetailUseCase.kt)).
- `infrastructure/`: Implementaciones de repositorios ([MoviesRepositoryImpl.kt](file:///d:/Kmp/Movieskmp/composeApp/src/commonMain/kotlin/io/albin/kmpmovies/infrastructure/repositories/MoviesRepositoryImpl.kt)), fuentes de datos Ktor ([MoviesRemoteDataSource.kt](file:///d:/Kmp/Movieskmp/composeApp/src/commonMain/kotlin/io/albin/kmpmovies/infrastructure/datasources/MoviesRemoteDataSource.kt)), DTOs y mappers ([MovieMapper.kt](file:///d:/Kmp/Movieskmp/composeApp/src/commonMain/kotlin/io/albin/kmpmovies/infrastructure/mappers/MovieMapper.kt)).
- `di/`: Contenedor e inyección de dependencias ([AppContainer.kt](file:///d:/Kmp/Movieskmp/composeApp/src/commonMain/kotlin/io/albin/kmpmovies/di/AppContainer.kt)).
- `presentation/`: Pantallas Compose ([HomeScreen.kt](file:///d:/Kmp/Movieskmp/composeApp/src/commonMain/kotlin/io/albin/kmpmovies/presentation/screens/home/HomeScreen.kt), [DetailScreen.kt](file:///d:/Kmp/Movieskmp/composeApp/src/commonMain/kotlin/io/albin/kmpmovies/presentation/screens/detail/DetailScreen.kt)), ViewModels ([HomeViewModel.kt](file:///d:/Kmp/Movieskmp/composeApp/src/commonMain/kotlin/io/albin/kmpmovies/presentation/viewmodel/home/HomeViewModel.kt), [DetailViewModel.kt](file:///d:/Kmp/Movieskmp/composeApp/src/commonMain/kotlin/io/albin/kmpmovies/presentation/viewmodel/detail/DetailViewModel.kt)) y componentes reutilizables ([LoadingIndicator.kt](file:///d:/Kmp/Movieskmp/composeApp/src/commonMain/kotlin/io/albin/kmpmovies/presentation/components/LoadingIndicator.kt)).
- `composeApp/src/androidMain`: Punto de entrada y configuración de Android.
- `composeApp/src/iosMain` & `iosApp`: Integración y proyecto Xcode para iOS.

## Ejecutar el proyecto

1. Abre el proyecto en Android Studio con soporte para Kotlin Multiplatform.
2. Para Android, ejecuta la configuración de `composeApp` en un emulador o dispositivo (Android 7.0 / API 24 o superior).
3. Para iOS, en macOS abre `iosApp/iosApp.xcodeproj` con Xcode y ejecuta la aplicación en un simulador o dispositivo.

### Configuración de la API Key

La aplicación consulta la API de TMDB. Por seguridad y buenas prácticas, la clave no se almacena en el código fuente ni en los recursos de la app.

Para configurarla:
1. Agrega tu clave en el archivo `local.properties` en la raíz del proyecto (este archivo está ignorado en Git):
   ```properties
   TMDB_API_KEY=tu_clave_de_tmdb_aqui
   ```
2. Alternativamente, puedes definir la variable de entorno `TMDB_API_KEY` en entornos de CI/CD.
