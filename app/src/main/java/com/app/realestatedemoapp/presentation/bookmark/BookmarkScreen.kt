package com.app.realestatedemoapp.presentation.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.app.realestatedemoapp.presentation.components.Banner
import com.app.realestatedemoapp.presentation.components.PropertiesLazyList
import kotlinx.coroutines.delay

@Composable
fun BookmarkScreen(viewModel: BookmarkViewModel = hiltViewModel()) {

    val bookmarkedProperties = viewModel.bookmarkedProperties?.collectAsLazyPagingItems()
    val errorMessage = viewModel.errorMessageId.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        PropertiesLazyList(
            bookmarkedProperties,
            onBookmark = { propertyId, isBookmarked ->
                viewModel.updateBookmark(propertyId, isBookmarked)
            }
        )

        errorMessage.value?.let { errorMsg ->
            LaunchedEffect(errorMsg) {
                delay(3000)
                viewModel.dismissError()
            }
            Banner(errorMsg = stringResource(errorMsg))
        }
    }
}