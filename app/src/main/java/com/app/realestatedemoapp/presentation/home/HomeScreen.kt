package com.app.realestatedemoapp.presentation.home


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val properties by viewModel.properties.collectAsStateWithLifecycle()

}