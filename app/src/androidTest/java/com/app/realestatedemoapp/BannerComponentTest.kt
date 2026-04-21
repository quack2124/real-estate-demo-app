package com.app.realestatedemoapp

import androidx.activity.ComponentActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.app.realestatedemoapp.presentation.components.Banner
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test


class BannerComponentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun isNoConnectionMessageDisplayed() {
        composeTestRule.setContent {
            val intFlow = flowOf((R.string.no_internet_connection)).collectAsState(initial = null)

            intFlow.value?.let { errorMsg ->
                val isOffline = errorMsg == R.string.no_internet_connection
                Banner(errorMsg = stringResource(errorMsg), isOfflineIcon = isOffline)
            }
        }
        val noConnection = composeTestRule.activity.getString(R.string.no_internet_connection)

        composeTestRule.onNodeWithText(noConnection).assertIsDisplayed()

    }

    @Test
    fun isFailedToFetchPropertiesMessageDisplayed() {
        composeTestRule.setContent {
            val intFlow =
                flowOf((R.string.failed_to_fetch_properties)).collectAsState(initial = null)

            intFlow.value?.let { errorMsg ->
                val isOffline = errorMsg == R.string.no_internet_connection
                Banner(errorMsg = stringResource(errorMsg), isOfflineIcon = isOffline)
            }
        }
        val failedToFetchPropertiesStr =
            composeTestRule.activity.getString(R.string.failed_to_fetch_properties)

        composeTestRule.onNodeWithText(failedToFetchPropertiesStr).assertIsDisplayed()

    }

    @Test
    fun isFailedToUpdateBookmarkStateMessageDisplayed() {
        composeTestRule.setContent {
            val intFlow =
                flowOf((R.string.failed_to_update_bookmark_state)).collectAsState(initial = null)

            intFlow.value?.let { errorMsg ->
                val isOffline = errorMsg == R.string.no_internet_connection
                Banner(errorMsg = stringResource(errorMsg), isOfflineIcon = isOffline)
            }
        }
        val failedToFetchPropertiesStr =
            composeTestRule.activity.getString(R.string.failed_to_update_bookmark_state)

        composeTestRule.onNodeWithText(failedToFetchPropertiesStr).assertIsDisplayed()

    }

    @Test
    fun isFailedToFetchBookmarkedPropertiesMessageDisplayed() {
        composeTestRule.setContent {
            val intFlow =
                flowOf((R.string.failed_to_fetch_bookmarked_properties)).collectAsState(initial = null)

            intFlow.value?.let { errorMsg ->
                val isOffline = errorMsg == R.string.no_internet_connection
                Banner(errorMsg = stringResource(errorMsg), isOfflineIcon = isOffline)
            }
        }
        val failedToFetchPropertiesStr =
            composeTestRule.activity.getString(R.string.failed_to_fetch_bookmarked_properties)

        composeTestRule.onNodeWithText(failedToFetchPropertiesStr).assertIsDisplayed()

    }
}
