package com.app.realestatedemoapp.presentation.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SignalWifiStatusbarConnectedNoInternet4
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.app.realestatedemoapp.R
import com.app.realestatedemoapp.domain.model.NetworkStatus
import com.app.realestatedemoapp.presentation.components.PropertiesLazyList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val properties = viewModel.properties.collectAsLazyPagingItems()
    val networkStatus = viewModel.networkStatus.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        if (networkStatus.value == NetworkStatus.Disconnected) {
            PropertiesLazyList(
                properties = properties,
                onBookmark = { propertyId, isBookmarked ->
                    viewModel.updateBookmark(propertyId, isBookmarked)
                }
            )

            Column(
                modifier = Modifier.align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.Red, RoundedCornerShape(15.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.SignalWifiStatusbarConnectedNoInternet4,
                            modifier = Modifier.size(24.dp),
                            tint = Color.White,
                            contentDescription = stringResource(
                                R.string.no_internet_icon
                            )
                        )
                        Text(
                            modifier = Modifier
                                .padding(10.dp),
                            text = stringResource(R.string.no_internet_connection),
                            color = Color.White
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .padding(15.dp)
                )
            }
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
    }
}
