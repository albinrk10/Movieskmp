package io.albin.kmpmovies.presentation.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.albin.kmpmovies.core.designsystem.AppTheme
import io.albin.kmpmovies.presentation.home.MovieFilter
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FilterChipsRowTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun filterChipsRow_displaysAllFilterLabels_andSelectsFilterOnClick() {
        var selectedFilter = MovieFilter.ALL

        composeTestRule.setContent {
            AppTheme {
                FilterChipsRow(
                    selectedFilter = selectedFilter,
                    onFilterSelected = { selectedFilter = it }
                )
            }
        }

        // Verify all 4 category labels are rendered
        composeTestRule.onNodeWithText("Todos").assertIsDisplayed()
        composeTestRule.onNodeWithText("Populares").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mejor valoradas").assertIsDisplayed()
        composeTestRule.onNodeWithText("Estrenos").assertIsDisplayed()

        // Click on "Mejor valoradas"
        composeTestRule.onNodeWithText("Mejor valoradas").performClick()
        assertEquals(MovieFilter.TOP_RATED, selectedFilter)
    }
}
