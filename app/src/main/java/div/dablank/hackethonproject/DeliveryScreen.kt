package div.dablank.hackethonproject

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DeliveryGameScreen(navController: NavController, startingLocation: LatLng) {
    var selectedRoute by remember { mutableStateOf<List<LatLng>?>(null) }
    var progress by remember { mutableStateOf(0f) }
    var timeRemaining by remember { mutableStateOf(60) }
    var trafficEvent by remember { mutableStateOf<String?>(null) }
    var gameOver by remember { mutableStateOf(false) }
    var deliveryCompleted by remember { mutableStateOf(false) }
    var deliveryPosition by remember { mutableStateOf(startingLocation) }
    var powerUp by remember { mutableStateOf<String?>(null) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startingLocation, 12f)
    }

    // Define routes
    val routeA = listOf(
        LatLng(19.0760, 72.8777),  // Mumbai
        LatLng(19.0822, 72.7411),
        LatLng(19.2206, 72.8479)
    )

    val routeB = listOf(
        LatLng(18.5204, 73.8567),  // Pune
        LatLng(18.5246, 73.8565),
        LatLng(18.5932, 73.9262)
    )

    // Game mechanics
    LaunchedEffect(selectedRoute) {
        if (selectedRoute != null) {
            while (progress < 1f && timeRemaining > 0) {
                delay(500)
                progress += 0.1f

                selectedRoute?.let {
                    val routeLength = it.size
                    val stepIndex = (progress * (routeLength - 1)).toInt()
                    deliveryPosition = it[stepIndex.coerceIn(0, routeLength - 1)]
                }

                timeRemaining -= 1
                if (timeRemaining <= 0) {
                    gameOver = true
                    break
                }

                // Random traffic events
                if ((1..100).random() < 20) {
                    trafficEvent = listOf(
                        "Heavy Traffic",
                        "Road Construction",
                        "Accident Ahead",
                        "Clear Roads",
                        "Traffic Jam",
                        "Police Checkpoint"
                    ).random()
                }

                // Random power-ups
                if ((1..100).random() < 15) {
                    powerUp = listOf("Speed Boost", "Extra Time", "None").random()
                }
            }

            if (progress >= 1f) {
                deliveryCompleted = true
            }
        }
    }

    // Reset game function
    fun resetGame() {
        selectedRoute = null
        progress = 0f
        timeRemaining = 60
        trafficEvent = null
        gameOver = false
        deliveryCompleted = false
        powerUp = null
    }

    // UI for game
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Delivery Game") })
        },
        content = {
            Column(modifier = Modifier.padding(16.dp)) {
                if (gameOver) {
                    Text(text = "Game Over! Time ran out.", color = Color.Red)
                    Button(onClick = { resetGame() }) {
                        Text("Try Again")
                    }
                } else if (deliveryCompleted) {
                    Text(text = "Delivery completed! Well done.", color = Color.Green)
                    Button(onClick = { resetGame() }) {
                        Text("Play Again")
                    }
                } else if (selectedRoute == null) {
                    // Route selection
                    Text(text = "Select Your Route", style = MaterialTheme.typography.headlineSmall)
                    Button(onClick = { selectedRoute = routeA }) {
                        Text("Route A")
                    }
                    Button(onClick = { selectedRoute = routeB }) {
                        Text("Route B")
                    }
                } else {
                    // Map display
                    Text(text = "Delivering...", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Time Remaining: $timeRemaining seconds", color = Color.Red)

                    Box(modifier = Modifier.fillMaxSize()) {
                        GoogleMap(
                            modifier = Modifier.fillMaxSize(),
                            cameraPositionState = cameraPositionState
                        ) {
                            selectedRoute?.let { route ->
                                Polyline(
                                    points = route,
                                    color = Color.Blue,
                                    width = 8f
                                )
                            }

                            selectedRoute?.let {
                                Marker(
                                    state = MarkerState(position = it.first()),
                                    title = "Start"
                                )
                                Marker(
                                    state = MarkerState(position = it.last()),
                                    title = "End"
                                )
                            }

                            Marker(
                                state = MarkerState(position = deliveryPosition),
                                title = "Delivery Vehicle"
                            )
                        }
                    }

                    trafficEvent?.let {
                        Text(text = "Traffic Event: $it", color = Color.Red)
                    }

                    powerUp?.let {
                        Text(text = "Power-Up: $it", color = Color.Green)
                    }
                }
            }
        }
    )
}
