package com.tamersarioglu.vibestation.presentation.screens.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tamersarioglu.vibestation.presentation.navigation.Screen
import com.tamersarioglu.vibestation.ui.theme.Accent1
import com.tamersarioglu.vibestation.ui.theme.Accent2
import com.tamersarioglu.vibestation.ui.theme.Accent3
import com.tamersarioglu.vibestation.ui.theme.Primary
import com.tamersarioglu.vibestation.ui.theme.PrimaryDark
import com.tamersarioglu.vibestation.ui.theme.Secondary
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController) {
    // Control visibility for exit animation
    var visible by remember { mutableStateOf(true) }

    // Auto-navigate after delay
    LaunchedEffect(key1 = true) {
        delay(2500) // Keep splash screen visible for 2.5 seconds
        visible = false
        delay(500) // Allow time for exit animation
        navController.navigate(Screen.RadioList.route) {
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }

    // Animation for icon scaling
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    // Animation for music bars (equalizer effect)
    val bar1Height by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bar1"
    )

    val bar2Height by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bar2"
    )

    val bar3Height by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bar3"
    )

    // Animate the text appearing
    val titleOpacity by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "titleOpacity"
    )

    val subtitleOpacity by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = 500,
            easing = FastOutSlowInEasing
        ),
        label = "subtitleOpacity"
    )

    // Add main content to the splash screen with enter/exit animations
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(700)) + scaleIn(animationSpec = tween(700)),
        exit = fadeOut(animationSpec = tween(500)) + scaleOut(animationSpec = tween(500))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            PrimaryDark,
                            Primary
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // App icon with animation
                Box(
                    modifier = Modifier
                        .scale(scale)
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color.White,
                                    Color.White.copy(alpha = 0.9f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Radio,
                        contentDescription = "App Icon",
                        modifier = Modifier.size(72.dp),
                        tint = Primary
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                // App name with fade-in animation
                Text(
                    text = "VibeStation",
                    color = Color.White.copy(alpha = titleOpacity),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Tagline with fade-in animation
                Text(
                    text = "Your Music, Your Vibe",
                    color = Color.White.copy(alpha = subtitleOpacity),
                    fontSize = 18.sp,
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(60.dp))

                // Animated equalizer bars
                Row(
                    modifier = Modifier.height(40.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // First bar
                    Box(
                        modifier = Modifier
                            .width(8.dp)
                            .height((40 * bar1Height).dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Accent1)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    // Second bar
                    Box(
                        modifier = Modifier
                            .width(8.dp)
                            .height((40 * bar2Height).dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Accent2)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    // Third bar
                    Box(
                        modifier = Modifier
                            .width(8.dp)
                            .height((40 * bar3Height).dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Secondary)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    // Fourth bar
                    Box(
                        modifier = Modifier
                            .width(8.dp)
                            .height((40 * bar2Height).dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Accent3)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    // Fifth bar
                    Box(
                        modifier = Modifier
                            .width(8.dp)
                            .height((40 * bar1Height).dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Accent1)
                    )
                }

                Spacer(modifier = Modifier.height(60.dp))

                // Loading indicator text
                Text(
                    text = "Loading...",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}