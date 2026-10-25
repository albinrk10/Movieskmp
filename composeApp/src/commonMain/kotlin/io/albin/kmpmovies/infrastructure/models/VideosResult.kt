package io.albin.kmpmovies.infrastructure.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideosResult(
    val id: Int = 0,
    val results: List<RemoteVideo> = emptyList()
)

@Serializable
data class RemoteVideo(
    val id: String = "",
    val key: String = "",
    val name: String = "",
    val site: String = "",
    val type: String = "",
    val official: Boolean = false,
    @SerialName("published_at") val publishedAt: String = ""
)
