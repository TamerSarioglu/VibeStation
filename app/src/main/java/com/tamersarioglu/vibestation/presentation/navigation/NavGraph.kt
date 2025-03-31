package com.tamersarioglu.vibestation.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tamersarioglu.vibestation.presentation.screens.radiolistscreen.RadioListScreen
import com.tamersarioglu.vibestation.presentation.screens.favorites.FavoritesScreen
import com.tamersarioglu.vibestation.presentation.screens.settings.SettingsScreen
import com.tamersarioglu.vibestation.presentation.screens.splash.SplashScreen

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object RadioList : Screen("radio_list")
    data object Favorites : Screen("favorites")
    data object Settings : Screen("settings")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.RadioList.route) {
            RadioListScreen(navController = navController)
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen(navController = navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
    }
}