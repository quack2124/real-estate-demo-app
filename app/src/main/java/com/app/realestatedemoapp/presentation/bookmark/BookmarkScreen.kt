package com.app.realestatedemoapp.presentation.bookmark

import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.app.realestatedemoapp.presentation.components.PropertiesLazyList

@Composable
fun BookmarkScreen(viewModel: BookmarkViewModel) {

    val bookmarkedProperties = viewModel.bookmarkedProperties.collectAsLazyPagingItems()

    PropertiesLazyList(
        bookmarkedProperties,
        onBookmark = { propertyId, isBookmarked ->
            viewModel.updateBookmark(propertyId, isBookmarked)
        }
    )
}