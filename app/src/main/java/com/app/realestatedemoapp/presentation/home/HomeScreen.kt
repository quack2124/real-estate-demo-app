package com.app.realestatedemoapp.presentation.home


import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.app.realestatedemoapp.presentation.components.PropertiesLazyList

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val properties = viewModel.properties.collectAsLazyPagingItems()

    PropertiesLazyList(
        properties = properties,
        onBookmark = { propertyId, isBookmarked ->
            viewModel.updateBookmark(propertyId, isBookmarked)
        }
    )
}
