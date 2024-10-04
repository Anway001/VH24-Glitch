package div.dablank.hackethonproject

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import div.dablank.hackethonproject.R

@Composable
fun HomeScreen(navController: NavHostController) {
    // Animated background gradient
    val colors = listOf(Color(0xFFBBDEFB), Color(0xFF4FC3F7), Color(0xFF1E88E5), Color(0xFF0D47A1))
    val transition = rememberInfiniteTransition()
    val color1 by transition.animateColor(
        initialValue = colors[0],
        targetValue = colors[1],
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = FastOutSlowInEasing)
        ), label = ""
    )
    val color2 by transition.animateColor(
        initialValue = colors[1],
        targetValue = colors[2],
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = FastOutSlowInEasing)
        ), label = ""
    )
    val color3 by transition.animateColor(
        initialValue = colors[2],
        targetValue = colors[3],
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = FastOutSlowInEasing)
        ), label = ""
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Draw the animated background
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(color1, color2, color3)
                )
            )
        }

        // Content over the background
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Title
            Text(
                text = "🚀 Welcome to the Glitch Delivery Training App! 🍔",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White // Change text color for visibility
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Add an image representing food delivery
            Image(
                painter = painterResource(id = R.drawable.parthc), // Replace with your drawable resource
                contentDescription = "Food Delivery Icon",
                modifier = Modifier.size(128.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Start Delivery Game Button
            Button(
                onClick = { navController.navigate("game_screen") },
                shape = RoundedCornerShape(12),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Green button
            ) {
                Text(text = "🎮 Start Delivery Game", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Leaderboard Button
            Button(
                onClick = { navController.navigate("leaderboard") },
                shape = RoundedCornerShape(12),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)) // Blue button
            ) {
                Text(text = "🏆 Leaderboard", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Check Map Button
            Button(
                onClick = { navController.navigate("map_screen") },
                shape = RoundedCornerShape(12),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)) // Orange button
            ) {
                Text(text = "🗺️ Check Map", color = Color.White)
            }
        }
    }
}
