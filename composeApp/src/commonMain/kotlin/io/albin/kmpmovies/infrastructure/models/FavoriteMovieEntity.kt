package io.albin.kmpmovies.infrastructure.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "favorite_movies")
@Serializable
data class FavoriteMovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val poster: String,
    val backdrop: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val overview: String,
    val originalTitle: String,
    val originalLanguage: String,
    val popularity: Double,
    val createdAt: Long = 0L
)
