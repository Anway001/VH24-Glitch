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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import div.dablank.hackethonproject.R
import kotlinx.coroutines.delay
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
        )
    )
    val color2 by transition.animateColor(
        initialValue = colors[1],
        targetValue = colors[2],
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = FastOutSlowInEasing)
        )
    )
    val color3 by transition.animateColor(
        initialValue = colors[2],
        targetValue = colors[3],
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = FastOutSlowInEasing)
        )
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

            // Training Tips
            var tipsIndex by remember { mutableStateOf(0) }
            val tips = listOf(
                "Tip 1: Know your shortcuts!",
                "Tip 2: Keep an eye on the fuel level.",
                "Tip 3: Be aware of peak traffic hours.",
                "Tip 4: Communicate with the customer."
            )
            Text(
                text = tips[tipsIndex],
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                modifier = Modifier.padding(16.dp)
            )

            // Automatically change tips every 5 seconds
            LaunchedEffect(Unit) {
                while (true) {
                    delay(5000)
                    tipsIndex = (tipsIndex + 1) % tips.size
                }
            }

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

            // Daily Challenge Button
            Button(
                onClick = { navController.navigate("animation_practice") },
                shape = RoundedCornerShape(12),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BFFF)) // Sky Blue button
            ) {
                Text(text = "🎨 Dash Board", color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Daily Status Button
            Button(
                onClick = { navController.navigate("view_challenges") }, // Navigate to Daily Status
                shape = RoundedCornerShape(12),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)) // Amber button
            ) {
                Text(text = "📊 Daily Status", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Feedback Section
            Text(
                text = "📝 Feedback from trainers:",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                modifier = Modifier.padding(vertical = 16.dp)
            )
            // Placeholder for feedback messages
            Text(
                text = "\"Great job on your last delivery!\"",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.LightGray),
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = "\"Remember to take shortcuts!\"",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.LightGray),
                modifier = Modifier.padding(4.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Location Overview Button
            Button(
                onClick = { navController.navigate("location_overview") },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853)),
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text(text = "Go to Location Overview", color = Color.White)
            }
        }
    }
}
