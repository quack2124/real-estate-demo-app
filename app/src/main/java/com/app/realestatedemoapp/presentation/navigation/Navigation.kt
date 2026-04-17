package com.app.realestatedemoapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.realestatedemoapp.presentation.bookmark.BookmarkScreen
import com.app.realestatedemoapp.presentation.home.HomeScreen

enum class Destination(
    val route: String,
    val label: String
) {
    Home(route = "home", label = "Home"),
    Bookmarks(route = "bookmarks", label = "Bookmarks")
}

val destinations = listOf(
    Destination.Home,
    Destination.Bookmarks
)

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier,
) {
    NavHost(
        navController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.Bookmarks -> BookmarkScreen()
                    Destination.Home -> HomeScreen()
                }
            }
        }
    }
}

