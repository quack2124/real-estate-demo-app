package com.app.realestatedemoapp

import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.app.realestatedemoapp.domain.model.PropertyModel
import com.app.realestatedemoapp.presentation.components.PropertiesLazyList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test


class PropertiesLazyListComponentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun onBookmarkBtnPressedBookmarkIconShouldChange() {
        var currentProperties by mutableStateOf(
            listOf(
                PropertyModel(
                    id = 123L,
                    title = "Test Property",
                    price = 100000,
                    locality = "Test Locality",
                    street = "Test Street",
                    imageUrl = "http://test.com",
                    isBookmarked = false,
                    currency = "USD"
                )
            )
        )

        composeTestRule.setContent {
            val lazyPagingItems =
                flowOf(PagingData.from(currentProperties)).collectAsLazyPagingItems()
            PropertiesLazyList(
                properties = lazyPagingItems, onBookmark = { id, newState ->
                    // simulate viewmodel updating the property
                    currentProperties = currentProperties.map {
                        if (it.id == id) it.copy(isBookmarked = newState) else it
                    }
                })
        }

        val outlinedIconDesc = composeTestRule.activity.getString(R.string.favorites_icon_outlined)
        val filledIconDesc = composeTestRule.activity.getString(R.string.favorites_icon_filled)

        composeTestRule.onNodeWithContentDescription(outlinedIconDesc).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(filledIconDesc).assertDoesNotExist()

        composeTestRule.onNodeWithContentDescription(outlinedIconDesc).performClick()

        composeTestRule.onNodeWithContentDescription(filledIconDesc).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(outlinedIconDesc).assertDoesNotExist()

        assertTrue("Property successfully bookmarked", currentProperties.first().isBookmarked)

        composeTestRule.onNodeWithContentDescription(filledIconDesc).performClick()

        composeTestRule.onNodeWithContentDescription(outlinedIconDesc).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(filledIconDesc).assertDoesNotExist()

        assertTrue("Property successfully unbookmarked", !currentProperties.first().isBookmarked)

    }

    @Test
    fun isProgressBarShownOnFetchingData() {
        composeTestRule.setContent {
            val lazyPagingItems: LazyPagingItems<PropertyModel> =
                flow {
                    delay(5000L)
                    emit(PagingData.empty<PropertyModel>())

                }.collectAsLazyPagingItems()
            PropertiesLazyList(
                properties = lazyPagingItems, onBookmark = { _, _ ->
                })
        }

        val loadingIconStr = composeTestRule.activity.getString(R.string.loading_indicator)
        composeTestRule.onNodeWithTag(loadingIconStr).assertIsDisplayed()
    }
}
