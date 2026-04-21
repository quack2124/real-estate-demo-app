package com.app.realestatedemoapp

import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class PropertiesLazyListComponentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    lateinit var currentProperties: List<PropertyModel>

    @Before
    fun before() {
        currentProperties = listOf(
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
    }

    @Test
    fun isAllTheContentShownInThePropertyListCard() {
        composeTestRule.setContent {
            val lazyPagingItems =
                flowOf(PagingData.from(currentProperties)).collectAsLazyPagingItems()
            PropertiesLazyList(
                properties = lazyPagingItems, onBookmark = { _, _ -> })
        }

        val imageStr = composeTestRule.activity.getString(R.string.property_image)

        composeTestRule.onNodeWithText(currentProperties.first().title).isDisplayed()
        composeTestRule.onNodeWithContentDescription(imageStr).isDisplayed()
        composeTestRule.onNodeWithText(currentProperties.first().price.toString(), substring = true)
            .isDisplayed()
        composeTestRule.onNodeWithText(currentProperties.first().locality, substring = true)
            .isDisplayed()
        composeTestRule.onNodeWithText(currentProperties.first().street, substring = true)
            .isDisplayed()
    }

    @Test
    fun onBookmarkBtnPressedBookmarkIconShouldChange() {
        var currentPropertiesState by mutableStateOf(currentProperties)
        composeTestRule.setContent {
            val lazyPagingItems =
                flowOf(PagingData.from(currentPropertiesState)).collectAsLazyPagingItems()
            PropertiesLazyList(
                properties = lazyPagingItems, onBookmark = { _, newState ->
                    // simulate viewmodel updating the property
                    currentPropertiesState =
                        currentPropertiesState.first().copy(isBookmarked = newState)
                            .let { property -> listOf(property) }

                })
        }

        val outlinedIconDesc = composeTestRule.activity.getString(R.string.favorites_icon_outlined)
        val filledIconDesc = composeTestRule.activity.getString(R.string.favorites_icon_filled)

        composeTestRule.onNodeWithContentDescription(outlinedIconDesc).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(filledIconDesc).assertDoesNotExist()

        composeTestRule.onNodeWithContentDescription(outlinedIconDesc).performClick()

        composeTestRule.onNodeWithContentDescription(filledIconDesc).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(outlinedIconDesc).assertDoesNotExist()

        assertTrue("Property successfully bookmarked", currentPropertiesState.first().isBookmarked)

        composeTestRule.onNodeWithContentDescription(filledIconDesc).performClick()

        composeTestRule.onNodeWithContentDescription(outlinedIconDesc).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(filledIconDesc).assertDoesNotExist()

        assertTrue(
            "Property successfully unbookmarked",
            !currentPropertiesState.first().isBookmarked
        )

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
