package com.app.realestatedemoapp.presentation.home


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.app.realestatedemoapp.presentation.components.PropertiesLazyList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val properties = viewModel.properties.collectAsLazyPagingItems()

    PullToRefreshBox(
        isRefreshing = false,
        onRefresh = { viewModel.refreshProperties() }
    ) {
        PropertiesLazyList(
            properties = properties,
            onBookmark = { propertyId, isBookmarked ->
                viewModel.updateBookmark(propertyId, isBookmarked)
            }
        )
    }
}
