package div.dablank.hackethonproject

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.delay

@Composable
fun MyNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = "home_screen") {
        composable("home_screen") { ViewChallengesScreen(navController) }
        composable("challenge_detail/{challengeId}") { backStackEntry ->
            val challengeId = backStackEntry.arguments?.getString("challengeId")
            ChallengeDetailScreen(challengeId)
        }
    }
}

@Composable
fun ViewChallengesScreen(navController: NavHostController) {
    // Sample challenges data
    val challenges = listOf(
        Challenge("Speed Challenge", "Deliver 5 packages in under 10 minutes.", 1, 10_000), // 10 seconds
        Challenge("Fuel Efficiency Challenge", "Complete 3 deliveries using less than 30% fuel.", 2, 20_000), // 20 seconds
        Challenge("Traffic Navigation Challenge", "Complete deliveries while avoiding traffic alerts.", 3, 15_000) // 15 seconds
    )

    // Gradient Background
    val gradientColors = listOf(Color(0xFFBBDEFB), Color(0xFF4FC3F7), Color(0xFF1E88E5))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(gradientColors))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🏆 View Challenges",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(challenges) { challenge ->
                    ChallengeCard(challenge, navController)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Floating Action Button to add new challenges
            FloatingActionButton(
                onClick = { /* Add your logic to create a new challenge */ },
                containerColor = Color(0xFF3B82F6),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp)
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Challenge", tint = Color.White)
            }
        }
    }
}

// Data class for Challenge
data class Challenge(val title: String, val description: String, val id: Int, val durationInMillis: Long)

@Composable
fun ChallengeCard(challenge: Challenge, navController: NavHostController) {
    var isExpanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = if (isExpanded) Color(0xFFE3F2FD) else Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .clickable { isExpanded = !isExpanded }
        ) {
            Text(text = challenge.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = challenge.description, style = MaterialTheme.typography.bodyMedium)
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { showDialog = true }
                ) {
                    Text(text = "Start Challenge")
                }
            }
        }
    }

    if (showDialog) {
        ChallengeConfirmationDialog(challenge, navController) { showDialog = false }
    }
}

@Composable
fun ChallengeConfirmationDialog(
    challenge: Challenge,
    navController: NavHostController,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDismiss()
                // Navigate to the detail screen and start the timer
                navController.navigate("challenge_detail/${challenge.id}") {
                    launchSingleTop = true // Avoid multiple instances
                }
            }) {
                Text("Start")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = {
            Text("Start Challenge?")
        },
        text = {
            Text("Are you ready to start the '${challenge.title}'?")
        }
    )
}

@Composable
fun ChallengeDetailScreen(challengeId: String?) {
    // Timer state
    var isTimerActive by remember { mutableStateOf(true) }
    var timerValue by remember { mutableStateOf(0L) } // Initialize to 0
    val challenge = getChallengeById(challengeId) // Implement this function to retrieve challenge details
    if (challenge != null) {
        timerValue = challenge.durationInMillis // Set timerValue to challenge duration

        // Start the timer
        LaunchedEffect(timerValue) {
            while (isTimerActive && timerValue > 0) {
                delay(1000) // 1 second delay
                timerValue -= 1000 // Reduce the timer
            }
            isTimerActive = false // Timer finished
        }
    }

    // Display timer and challenge details
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Challenge ID: $challengeId", style = MaterialTheme.typography.headlineSmall)
        Text(text = "Time Remaining: ${timerValue / 1000} seconds", style = MaterialTheme.typography.bodyLarge)
        // Implement additional UI elements to display challenge details
    }
}

// Function to get Challenge by ID (implement this based on your data source)
fun getChallengeById(challengeId: String?): Challenge? {
    // Sample challenges data
    val challenges = listOf(
        Challenge("Speed Challenge", "Deliver 5 packages in under 10 minutes.", 1, 10_000),
        Challenge("Fuel Efficiency Challenge", "Complete 3 deliveries using less than 30% fuel.", 2, 20_000),
        Challenge("Traffic Navigation Challenge", "Complete deliveries while avoiding traffic alerts.", 3, 15_000)
    )

    return challenges.find { it.id.toString() == challengeId }
}
