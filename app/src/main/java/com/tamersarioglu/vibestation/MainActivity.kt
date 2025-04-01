package com.tamersarioglu.vibestation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tamersarioglu.vibestation.presentation.components.BottomNavigation
import com.tamersarioglu.vibestation.presentation.navigation.NavGraph
import com.tamersarioglu.vibestation.presentation.navigation.Screen
import com.tamersarioglu.vibestation.ui.theme.VibeStationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VibeStationTheme {
                val navController = rememberNavController()
                
                // Get current route to determine if we should show the bottom navigation
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                
                // Only show bottom navigation when not on splash screen
                val showBottomNav = currentRoute != null && currentRoute != Screen.Splash.route
                
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { 
                        BottomNavigation(
                            navController = navController,
                            isVisible = showBottomNav
                        )
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavGraph(navController = navController)
                    }
                }
            }
        }
    }
}