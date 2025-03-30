package com.tamersarioglu.vibestation.presentaion.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.tamersarioglu.vibestation.presentaion.navigation.Screen

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        Screen.RadioList,
        Screen.Favorites,
        Screen.Search,
        Screen.Settings
    )

    val icons = mapOf(
        Screen.RadioList to Icons.AutoMirrored.Filled.List,
        Screen.Favorites to Icons.Default.Favorite,
        Screen.Search to Icons.Default.Search,
        Screen.Settings to Icons.Default.Settings
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(icons[screen]!!, contentDescription = screen.route) },
                label = { Text(screen.route.replaceFirstChar { it.uppercase() }) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
} 