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

## Estructura del proyecto

- `composeApp/src/commonMain`: interfaz, navegación, modelos y acceso a datos compartidos.
- `composeApp/src/androidMain`: punto de entrada y configuración específica de Android.
- `composeApp/src/iosMain`: integración de Compose con iOS.
- `iosApp`: proyecto de Xcode y punto de entrada de la aplicación iOS.

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
