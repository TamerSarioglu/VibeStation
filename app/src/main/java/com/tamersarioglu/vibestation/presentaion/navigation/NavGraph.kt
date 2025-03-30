package com.tamersarioglu.vibestation.presentaion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tamersarioglu.vibestation.presentaion.screens.radiolistscreen.RadioListScreen
import com.tamersarioglu.vibestation.presentaion.screens.favorites.FavoritesScreen
import com.tamersarioglu.vibestation.presentaion.screens.search.SearchScreen
import com.tamersarioglu.vibestation.presentaion.screens.settings.SettingsScreen

sealed class Screen(val route: String) {
    object RadioList : Screen("radio_list")
    object Favorites : Screen("favorites")
    object Search : Screen("search")
    object Settings : Screen("settings")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.RadioList.route
    ) {
        composable(Screen.RadioList.route) {
            RadioListScreen(navController = navController)
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen(navController = navController)
        }
        composable(Screen.Search.route) {
            SearchScreen(navController = navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
    }
} 