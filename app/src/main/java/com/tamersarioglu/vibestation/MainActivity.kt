package com.tamersarioglu.vibestation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tamersarioglu.vibestation.domain.model.RadioStation
import com.tamersarioglu.vibestation.presentation.components.BottomNavigation
import com.tamersarioglu.vibestation.presentation.components.PlayingSectionBottomSheet
import com.tamersarioglu.vibestation.presentation.navigation.NavGraph
import com.tamersarioglu.vibestation.presentation.navigation.Screen
import com.tamersarioglu.vibestation.ui.theme.VibeStationTheme
import com.tamersarioglu.vibestation.utils.ExoPlayerHandler
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VibeStationTheme {
                val navController = rememberNavController()
                val context = LocalContext.current
                val exoPlayerHandler = remember { ExoPlayerHandler(context) }
                var playingStation by remember { mutableStateOf<RadioStation?>(null) }
                
                // Get current route to determine if we should show the bottom navigation
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                
                // Only show bottom navigation when not on splash screen
                val showBottomNav = currentRoute != null && currentRoute != Screen.Splash.route
                
                val skipPartiallyExpanded by rememberSaveable { mutableStateOf(false) }
                val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)

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
                        NavGraph(
                            navController = navController,
                            onStationClick = { station ->
                                playingStation = station
                                exoPlayerHandler.playStation(station)
                            }
                        )

                        // Playing station bottom sheet
                        playingStation?.let { station ->
                            PlayingSectionBottomSheet(
                                station = station,
                                onClose = {
                                    playingStation = null
                                    exoPlayerHandler.stopStation()
                                },
                                sheetState = bottomSheetState,
                                exoPlayerHandler = exoPlayerHandler
                            )
                        }
                    }
                }
            }
        }
    }
}