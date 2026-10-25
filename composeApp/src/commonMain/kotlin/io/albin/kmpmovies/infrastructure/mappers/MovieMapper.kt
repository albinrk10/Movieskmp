package io.albin.kmpmovies.infrastructure.mappers

import io.albin.kmpmovies.core.constants.Constants
import io.albin.kmpmovies.domain.entities.Movie
import io.albin.kmpmovies.infrastructure.models.RemoteMovie

fun RemoteMovie.toDomain(): Movie = Movie(
    id = id,
    title = title,
    overview = if (overview.isNotBlank()) overview else "Sin sinopsis disponible en español.",
    releaseDate = releaseDate,
    poster = if (!posterPath.isNullOrEmpty()) "${Constants.IMAGE_BASE_URL_W185}/$posterPath" else "",
    backdrop = backdropPath?.let { "${Constants.IMAGE_BASE_URL_W780}/$it" },
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    popularity = popularity,
    voteAverage = voteAverage
)
