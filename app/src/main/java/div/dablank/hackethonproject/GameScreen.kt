package div.dablank.hackethonproject

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
    employeeName: String // Employee's name for personalization
) {
    // State Variables
    var timeLeft by remember { mutableStateOf(300) } // Timer in seconds (5 minutes)
    var fuelLeft by remember { mutableStateOf(100f) } // Fuel percentage
    var trafficAlert by remember { mutableStateOf(false) } // Simulated traffic event
    var currentLocation by remember { mutableStateOf<LocationData?>(null) } // Current location
    var deliveriesMade by remember { mutableStateOf(0) } // Count of deliveries made
    var leaderboardData by remember { mutableStateOf(mutableListOf<Player>()) } // Leaderboard data

    // Permission launcher for location
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                // Permissions granted, request location updates
                locationUtils.requestLocationUpdates(viewModel)
            } else {
                // Permissions denied
                Toast.makeText(context, "Location permission is required.", Toast.LENGTH_LONG).show()
            }
        }
    )

    // Countdown Timer
    LaunchedEffect(timeLeft) {
        while (timeLeft > 0) {
            delay(1000L) // 1 second delay
            timeLeft--
        }
        // When time runs out
        val player = Player(employeeName, deliveriesMade)
        leaderboardData.add(player) // Add player to leaderboard
        navigateToLeaderboard(navController, player)
    }

    // Observe location changes
    LaunchedEffect(viewModel.location.value) {
        currentLocation = viewModel.location.value // Update current location
    }

    // UI Layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF3F4F6)) // Light gray background
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🚴‍♂️ ${employeeName}'s Delivery Challenge",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF3B82F6) // Blue color for the title
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Countdown Timer
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFBBE0F1) // Light blue for the timer card
        ) {
            Text(
                text = "⏳ Time Left: ${timeLeft / 60}:${(timeLeft % 60).toString().padStart(2, '0')}",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Fuel Gauge
        FuelGauge(fuelLeft)

        Spacer(modifier = Modifier.height(16.dp))

        // Random traffic event
        TrafficAlertButton(trafficAlert) {
            trafficAlert = (0..10).random() > 7
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Current location display
        LocationDisplay(currentLocation)

        Spacer(modifier = Modifier.height(16.dp))

        // Map placeholder and delivery address selection
        SelectDeliveryAddressButton(locationUtils, navController, requestPermissionLauncher, context, viewModel)

        Spacer(modifier = Modifier.height(16.dp))

        // Deliver to Customer Button
        DeliverButton(
            employeeName = employeeName,
            fuelLeft = fuelLeft,
            deliveriesMade = deliveriesMade,
            onDeliveryMade = { deliveriesMade++ },
            onFuelConsumed = { fuelLeft -= 20f },
            onGameOver = {
                leaderboardData.add(Player(employeeName, deliveriesMade))
                navigateToLeaderboard(navController, Player(employeeName, deliveriesMade))
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Displaying deliveries made
        Text(text = "Deliveries Made: $deliveriesMade", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Leaderboard Display
        LeaderboardDisplay(leaderboardData)
    }
}

// Function to navigate to the leaderboard
private fun navigateToLeaderboard(navController: NavHostController, player: Player) {
    navController.navigate("leaderboard") {
        popUpTo("game_screen") { inclusive = true }
        launchSingleTop = true
    }
}

// Composable for Fuel Gauge
@Composable
fun FuelGauge(fuelLeft: Float) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFE0F7FA) // Light cyan for the fuel card
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "⛽ Fuel: ${fuelLeft.toInt()}%", style = MaterialTheme.typography.headlineLarge)
            LinearProgressIndicator(
                progress = fuelLeft / 100,
                color = Color.Green,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
        }
    }
}

// Composable for Traffic Alert Button
@Composable
fun TrafficAlertButton(trafficAlert: Boolean, onClick: () -> Unit) {
    if (trafficAlert) {
        Text(
            text = "🚧 Traffic Alert! Take an alternate route.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Red
        )
    } else {
        Button(
            onClick = onClick,
            modifier = Modifier
                .background(Color(0xFFBBDEFB)) // Light blue background for the button
                .padding(12.dp)
                .border(1.dp, Color(0xFF90CAF9), RoundedCornerShape(8.dp))
        ) {
            Text(text = "Continue Route", color = Color.Black)
        }
    }
}

// Composable for Location Display
@Composable
fun LocationDisplay(currentLocation: LocationData?) {
    currentLocation?.let {
        Text(text = "📍 Current Location: ${it.latitude}, ${it.longitude}", style = MaterialTheme.typography.bodyLarge)
    } ?: run {
        Text(text = "🔍 Getting current location...", style = MaterialTheme.typography.bodyLarge)
    }
}

// Composable for Select Delivery Address Button
@Composable
fun SelectDeliveryAddressButton(
    locationUtils: LocationUtils,
    navController: NavHostController,
    requestPermissionLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
    context: Context,
    viewModel: LocationViewModel // Pass the viewModel here
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
    ) {
        Button(onClick = {
            if (locationUtils.hasLocationPermission(context)) {
                locationUtils.requestLocationUpdates(viewModel) // Ensure you have a reference to the viewModel here
                navController.navigate("locationscreen") {
                    launchSingleTop = true
                }
            } else {
                requestPermissionLauncher.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ))
            }
        }) {
            Text("Select Delivery Address", color = Color.Black)
        }
    }
}

// Composable for Deliver Button
@Composable
fun DeliverButton(
    employeeName: String,
    fuelLeft: Float,
    deliveriesMade: Int,
    onDeliveryMade: () -> Unit,
    onFuelConsumed: () -> Unit,
    onGameOver: () -> Unit
) {
    Button(
        onClick = {
            if (fuelLeft > 0) {
                onFuelConsumed() // Simulate fuel consumption
                onDeliveryMade() // Increase delivery count
                if (fuelLeft <= 0f) {
                    onGameOver() // End game if fuel runs out
                }
            }
        },
        modifier = Modifier
            .background(Color(0xFFB2DFDB)) // Light teal for the button
            .padding(12.dp)
            .border(1.dp, Color(0xFF009688), RoundedCornerShape(8.dp))
    ) {
        Text(text = "🚚 Deliver to Customer", color = Color.Black)
    }
}

// Composable for Leaderboard Display
@Composable
fun LeaderboardDisplay(leaderboardData: List<Player>) {
    Text(
        text = "🏆 Leaderboard",
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
        modifier = Modifier.padding(16.dp)
    )

    LazyColumn {
        items(leaderboardData.sortedByDescending { it.score }) { player -> // Sort by score
            LeaderboardPlayerRow(player, rank = leaderboardData.indexOf(player) + 1)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

// Leaderboard player row Composable
@Composable
fun LeaderboardPlayerRow(player: Player, rank: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$rank. ${player.name}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.weight(1f)) // Pushes score to the end
        Text(text = player.score.toString(), style = MaterialTheme.typography.bodyLarge)
    }
}

// Data class for Player

