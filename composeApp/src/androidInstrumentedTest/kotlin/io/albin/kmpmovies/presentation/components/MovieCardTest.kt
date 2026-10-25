package io.albin.kmpmovies.presentation.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.albin.kmpmovies.core.designsystem.AppTheme
import io.albin.kmpmovies.domain.entities.Movie
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val testMovie = Movie(
        id = 101,
        title = "Interstellar",
        overview = "Viaje a través de un agujero de gusano.",
        releaseDate = "2014-11-07",
        poster = "https://image.tmdb.org/t/p/w500/test.jpg",
        backdrop = null,
        originalTitle = "Interstellar",
        originalLanguage = "en",
        popularity = 89.0,
        voteAverage = 8.7
    )

    @Test
    fun movieCard_displaysTitleAndYear_andTriggersClick() {
        var clicked = false

        composeTestRule.setContent {
            AppTheme {
                MovieCard(
                    movie = testMovie,
                    onClick = { clicked = true }
                )
            }
        }

        // Verify title is rendered and displayed
        composeTestRule.onNodeWithText("Interstellar").assertIsDisplayed()

        // Verify year and language are displayed
        composeTestRule.onNodeWithText("2014").assertIsDisplayed()
        composeTestRule.onNodeWithText(" • EN").assertIsDisplayed()

        // Verify click interaction
        composeTestRule.onNodeWithText("Interstellar").performClick()
        assertTrue("MovieCard click callback should have been called", clicked)
    }
}
