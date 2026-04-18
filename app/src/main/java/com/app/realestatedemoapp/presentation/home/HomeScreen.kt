package com.app.realestatedemoapp.presentation.home


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.app.realestatedemoapp.R

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val properties = viewModel.properties.collectAsLazyPagingItems()

    LazyColumn(
        contentPadding = PaddingValues(10.dp)
    ) {
        items(
            items = properties.itemSnapshotList.items, key = { property ->
                property.id
            }) { item ->
            Card(
                border = BorderStroke(2.dp, Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        AsyncImage(
                            model = item.imageUrl,
                            contentDescription = stringResource(R.string.property_image),
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            text = item.getFormattedPrice(),
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                                .background(Color.White, RoundedCornerShape(8.dp))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                        IconButton(
                            onClick = { viewModel.updateBookmark(item.id, !item.isBookmarked) },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(horizontal = 6.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = if (item.isBookmarked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                tint = Color.Red,
                                contentDescription = stringResource(R.string.favorites_icon)
                            )
                        }

                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black
                        )
                        Row {
                            Icon(
                                imageVector = Icons.Filled.Map,
                                contentDescription = stringResource(R.string.location_icon),
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                text = item.getFullAddress(),
                            )
                        }
                    }
                }
            }
        }
    }
}
