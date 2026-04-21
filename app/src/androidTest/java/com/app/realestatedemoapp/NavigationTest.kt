package com.app.realestatedemoapp

import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.app.realestatedemoapp.navigation.Destination
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() = hiltRule.inject()

    @Test
    fun isHomeScreenSelectedOnStart() {
        composeTestRule.apply {
            onNodeWithText(Destination.Home.label).assertIsSelected()
        }
    }

    @Test
    fun isBookmarksScreenSelected() {
        composeTestRule.apply {
            onNodeWithText(Destination.Bookmarks.label).performClick()
            onNodeWithText(Destination.Bookmarks.label).assertIsSelected()
        }
    }

    @Test
    fun isHomeScreenSelected() {
        composeTestRule.apply {
            onNodeWithText(Destination.Bookmarks.label).performClick()
            onNodeWithText(Destination.Home.label).performClick()
            onNodeWithText(Destination.Home.label).assertIsSelected()
        }
    }
}