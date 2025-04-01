package com.tamersarioglu.vibestation.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tamersarioglu.vibestation.ui.theme.Primary
import com.tamersarioglu.vibestation.ui.theme.PrimaryDark
import com.tamersarioglu.vibestation.ui.theme.PrimaryLight

@Composable
fun EnhancedHeader(
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier
) {
    // Create a beautiful gradient for the header
    val gradientColors = listOf(
        PrimaryDark,
        Primary,
        PrimaryLight.copy(alpha = 0.8f)
    )
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(gradientColors)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 40.dp, bottom = 24.dp)
        ) {
            // Main title with enhanced typography
            Text(
                text = title,
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp,
                style = MaterialTheme.typography.headlineLarge
            )
            
            // Optional subtitle
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 16.sp,
                    letterSpacing = 0.sp,
                    modifier = Modifier.padding(top = 4.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}