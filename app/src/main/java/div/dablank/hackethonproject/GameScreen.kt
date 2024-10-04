package div.dablank.hackethonproject

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import div.dablank.myproject.LocationViewModel
import kotlinx.coroutines.delay

@Composable
fun GameScreen(
    navController: NavHostController,
    locationUtils: LocationUtils,
    viewModel: LocationViewModel,
    context: Context,
    address: String // This can be passed based on location selection
) {
    var timeLeft by remember { mutableStateOf(300) } // Timer in seconds (5 minutes)
    var fuelLeft by remember { mutableStateOf(100f) } // Fuel percentage
    var trafficAlert by remember { mutableStateOf(false) } // Simulated traffic event
    var currentLocation by remember { mutableStateOf<LocationData?>(null) } // Current location

    // Define the permission launcher
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                // Permissions granted, request location updates
                locationUtils.requestLocationUpdates(viewModel)
            } else {
                // Permissions denied, show a message to the user
                Toast.makeText(context, "Location permission is required.", Toast.LENGTH_LONG).show()
            }
        }
    )

    // Countdown Timer
    LaunchedEffect(timeLeft) {
        if (timeLeft > 0) {
            delay(1000L) // 1 second delay
            timeLeft -= 1
        } else {
            navController.navigate("leaderboard") // Navigate to leaderboard if time runs out
        }
    }

    // Observe location changes
    LaunchedEffect(viewModel.location.value) {
        currentLocation = viewModel.location.value // Update current location
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Delivery in Progress...", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Show the countdown timer
        Text(
            text = "Time Left: ${timeLeft / 60}:${(timeLeft % 60).toString().padStart(2, '0')}",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Fuel Gauge
        Text(text = "Fuel: ${fuelLeft.toInt()}%", style = MaterialTheme.typography.headlineLarge)
        LinearProgressIndicator(
            progress = fuelLeft / 100,
            color = Color.Green,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Random traffic event
        if (trafficAlert) {
            Text(
                text = "🚧 Traffic Alert! Take an alternate route.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Red
            )
        } else {
            Button(onClick = {
                trafficAlert = (0..10).random() > 7 // Random chance of traffic
            }) {
                Text(text = "Continue Route")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Current location display
        currentLocation?.let {
            Text(text = "Current Location: ${it.latitude}, ${it.longitude}", style = MaterialTheme.typography.bodyLarge)
        } ?: run {
            Text(text = "Getting current location...", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Map placeholder (you can integrate a real map API like Google Maps here)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.LightGray)
        ) {
            Button(onClick = {
                if (locationUtils.hasLocationPermission(context)) {
                    locationUtils.requestLocationUpdates(viewModel)
                } else {
                    requestPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
            }) {
                Text("Address: $address") // Show selected address
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Logic to finish delivery can be added here
            fuelLeft -= 20f // Simulate fuel consumption
            if (fuelLeft <= 0f) {
                navController.navigate("leaderboard") // End game if fuel runs out
            }
        }) {
            Text(text = "Deliver to Customer")
        }
    }
}
