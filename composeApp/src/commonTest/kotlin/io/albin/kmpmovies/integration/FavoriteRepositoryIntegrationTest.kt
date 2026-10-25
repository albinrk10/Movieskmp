package io.albin.kmpmovies.integration

import app.cash.turbine.test
import io.albin.kmpmovies.domain.entities.Movie
import io.albin.kmpmovies.fakes.FakeFavoriteMovieDao
import io.albin.kmpmovies.infrastructure.repositories.FavoriteRepositoryImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FavoriteRepositoryIntegrationTest {

    private val sampleMovie = Movie(
        id = 42,
        title = "Inception",
        overview = "A thief who steals corporate secrets through dream-sharing.",
        releaseDate = "2010-07-16",
        poster = "/inception.jpg",
        backdrop = "/inception_bg.jpg",
        originalTitle = "Inception",
        originalLanguage = "en",
        popularity = 85.0,
        voteAverage = 8.8
    )

    @Test
    fun `toggleFavorite adds movie if not in favorites and removes it if already present`() = runTest {
        val dao = FakeFavoriteMovieDao()
        val repository = FavoriteRepositoryImpl(dao)

        // Initial state: not favorite
        repository.isFavorite(sampleMovie.id).test {
            assertFalse(awaitItem())

            // 1. First toggle -> Adds to favorites
            repository.toggleFavorite(sampleMovie)
            assertTrue(awaitItem())

            // 2. Second toggle -> Removes from favorites
            repository.toggleFavorite(sampleMovie)
            assertFalse(awaitItem())
        }
    }

    @Test
    fun `getFavorites emits updated list of domain movies when favorites change`() = runTest {
        val dao = FakeFavoriteMovieDao()
        val repository = FavoriteRepositoryImpl(dao)

        repository.getFavorites().test {
            // Initially empty
            assertEquals(0, awaitItem().size)

            // Add movie
            repository.toggleFavorite(sampleMovie)
            val updatedList = awaitItem()
            assertEquals(1, updatedList.size)
            assertEquals("Inception", updatedList.first().title)
            assertEquals(8.8, updatedList.first().voteAverage)

            // Remove movie
            repository.toggleFavorite(sampleMovie)
            assertEquals(0, awaitItem().size)
        }
    }
}
