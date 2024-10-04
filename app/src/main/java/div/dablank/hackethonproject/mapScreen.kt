package div.dablank.hackethonproject

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun RealisticMapWindow(navController: NavHostController) {
    var showMarkers by remember { mutableStateOf(true) }
    var currentLocation by remember { mutableStateOf("Current Location: Not Available") }
    var destination by remember { mutableStateOf("Destination: Not Set") }
    val markers = remember { mutableStateListOf<MarkerData>() } // List to hold marker data

    // Simulate updating the current location and destination
    LaunchedEffect(Unit) {
        while (true) {
            currentLocation = "Current Location: ${Random.nextDouble(-90.0, 90.0)}, ${Random.nextDouble(-180.0, 180.0)}"
            destination = "Destination: ${Random.nextDouble(-90.0, 90.0)}, ${Random.nextDouble(-180.0, 180.0)}"
            delay(3000) // Update every 3 seconds
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Brush.verticalGradient(listOf(Color(0xFF8EC5FC), Color(0xFFE0C3FC)))),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Header with fun color and custom font
        Text(
            text = "🗺️ Interactive Map View",
            style = MaterialTheme.typography.headlineMedium.copy(color = Color(0xFF6200EA)),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Location Info with glowing effect for more visual interest
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            GlowingText(currentLocation)
            GlowingText(destination)
        }

        // Simulated Map Area with Texture
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(16.dp)) // Rounded corners
                .shadow(8.dp) // Shadow for depth
                .background(Color(0xFFD7E9F7))
        ) {
            // Background image to simulate a map
            Image(
                painter = painterResource(id = R.drawable.dabalnk), // Your textured map image
                contentDescription = "Map Background",
                modifier = Modifier.fillMaxSize()
            )

            // Animated Markers
            if (showMarkers) {
                for (marker in markers) {
                    Marker(
                        positionX = marker.positionX,
                        positionY = marker.positionY,
                        label = marker.label,
                        onClick = { /* Handle marker click, maybe show more info */ }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Action Buttons with creative colors and shadows
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = { showMarkers = !showMarkers },
                colors = ButtonDefaults.buttonColors(Color(0xFF6200EA)),
                modifier = Modifier.shadow(6.dp, RoundedCornerShape(12.dp))
            ) {
                Text(if (showMarkers) "Hide Markers" else "Show Markers", color = Color.White)
            }

            Button(
                onClick = { /* Logic to reset view */ },
                colors = ButtonDefaults.buttonColors(Color(0xFF03DAC5)),
                modifier = Modifier.shadow(6.dp, RoundedCornerShape(12.dp))
            ) {
                Text("Reset View", color = Color.White)
            }

            FloatingActionButton(
                onClick = {
                    // Add a new marker at a random position
                    markers.add(
                        MarkerData(
                        positionX = Random.nextInt(0, 300).dp,
                        positionY = Random.nextInt(0, 400).dp,
                        label = "Marker ${markers.size + 1}"
                    )
                    )
                },
                containerColor = Color(0xFF03DAC5),
                modifier = Modifier.padding(16.dp)
            ) {
                Text("+", color = Color.White)
            }
        }
    }
}

@Composable
fun GlowingText(text: String) {
    Box(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .graphicsLayer {
                shadowElevation = 8.dp.toPx() // Add shadow for glowing effect
                shape = CircleShape
                clip = true
            }
            .background(Color.White.copy(alpha = 0.15f))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF6200EA)),
        )
    }
}

// Simulated Marker Data Class
data class MarkerData(val positionX: Dp, val positionY: Dp, val label: String)

// Simulated Marker Composable with animation and scaling effect
@Composable
fun Marker(positionX: Dp, positionY: Dp, label: String, onClick: () -> Unit) {
    var markerVisible by remember { mutableStateOf(true) }
    val animatedOffsetX by animateDpAsState(targetValue = if (markerVisible) positionX else 0.dp, animationSpec = tween(600))
    val animatedOffsetY by animateDpAsState(targetValue = if (markerVisible) positionY else 0.dp, animationSpec = tween(600))
    val animatedScale by animateFloatAsState(targetValue = if (markerVisible) 1.1f else 1f, animationSpec = tween(600))

    Box(
        modifier = Modifier
            .size(48.dp)
            .background(Color.Red, shape = CircleShape)
            .offset(x = animatedOffsetX, y = animatedOffsetY)
            .clickable(onClick = onClick)
            .graphicsLayer {
                scaleX = animatedScale
                scaleY = animatedScale
            }, // Scaling effect for interactivity
        contentAlignment = Alignment.Center
    ) {
        Text(text = label.first().toString(), color = Color.White, modifier = Modifier.padding(4.dp))
    }
}
