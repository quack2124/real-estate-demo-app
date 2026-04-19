package com.app.realestatedemoapp.presentation.components

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.realestatedemoapp.R

@Composable
fun Banner(modifier: Modifier = Modifier, errorMsg: String, isOfflineIcon: Boolean = false) {
    Box(
        modifier = modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.background(Color.Red, RoundedCornerShape(15.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = if (isOfflineIcon) painterResource(R.drawable.ic_no_wifi_24dp) else painterResource(
                            R.drawable.ic_error_24dp
                        ),
                        modifier = Modifier.size(24.dp),
                        tint = Color.White,
                        contentDescription = stringResource(
                            R.string.no_internet_icon
                        )
                    )
                    Text(
                        modifier = Modifier.padding(10.dp), text = errorMsg, color = Color.White
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
    }
}