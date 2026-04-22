package com.app.realestatedemoapp.presentation.home


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.app.realestatedemoapp.R
import com.app.realestatedemoapp.domain.model.NetworkStatus
import com.app.realestatedemoapp.presentation.components.Banner
import com.app.realestatedemoapp.presentation.components.PropertiesLazyList
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {

    val properties = viewModel.properties.collectAsLazyPagingItems()
    val networkStatus = viewModel.networkStatus.collectAsStateWithLifecycle()
    val errorMessage = viewModel.errorMessage.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        if (networkStatus.value == NetworkStatus.Disconnected) {
            PropertiesLazyList(
                properties = properties,
                onBookmark = { propertyId, isBookmarked ->
                    viewModel.updateBookmark(propertyId, isBookmarked)
                }
            )

        } else {
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
        networkStatus.value.takeIf { it == NetworkStatus.Disconnected }?.let {
            Banner(errorMsg = stringResource(R.string.no_internet_connection), isOfflineIcon = true)
        }

        errorMessage.value?.let { errorMsg ->
            LaunchedEffect(errorMsg) {
                delay(3000)
                viewModel.dismissError()
            }
            Banner(errorMsg = stringResource(errorMsg), isOfflineIcon = false)
        }
        if (properties.loadState.hasError) {
            LaunchedEffect(R.string.failed_to_fetch_properties) {
                delay(3000)
                viewModel.dismissError()
            }
            Banner(
                errorMsg = stringResource(R.string.failed_to_fetch_properties),
                isOfflineIcon = false
            )
        }
    }
}
