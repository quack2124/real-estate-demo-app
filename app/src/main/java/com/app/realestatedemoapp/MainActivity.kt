package com.app.realestatedemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.app.realestatedemoapp.navigation.AppNavHost
import com.app.realestatedemoapp.navigation.Destination
import com.app.realestatedemoapp.navigation.destinations
import com.app.realestatedemoapp.ui.theme.RealEstateDemoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RealEstateDemoAppTheme {
                Root()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Root() {
    val navController = rememberNavController()
    val startDestination = Destination.Home
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(title = {
                    Text(
                        text = stringResource(R.string.real_estate_demo_app),
                        style = MaterialTheme.typography.titleLarge
                    )
                })
                PrimaryTabRow(selectedTabIndex = selectedDestination) {
                    destinations.forEachIndexed { index, destination ->
                        Tab(selected = selectedDestination == index, onClick = {
                            navController.navigate(route = destination.route)
                            selectedDestination = index

                        }, text = { Text(destination.label) })
                    }
                }
            }
        },
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            startDestination = startDestination

        )
    }
}
