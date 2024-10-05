package div.dablank.hackethonproject

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AnimationPracticeScreen(navController: NavHostController) {
    // Sample data for dashboard
    var totalDeliveries by remember { mutableStateOf(50) }
    var successfulDeliveries by remember { mutableStateOf(45) }
    var averageRating by remember { mutableStateOf(4.5) }
    var challengesCompleted by remember { mutableStateOf(3) }
    var totalChallenges by remember { mutableStateOf(5) }
    var rewards by remember { mutableStateOf(100) } // Points

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF3F4F6)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "📦 Delivery Dashboard",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF3B82F6)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Delivery Stats
        StatsCard(totalDeliveries, successfulDeliveries, averageRating)

        Spacer(modifier = Modifier.height(16.dp))

        // Daily Challenges Section
        ChallengesCard(challengesCompleted, totalChallenges)

        Spacer(modifier = Modifier.height(16.dp))

        // Rewards Section
        RewardsCard(rewards)

        Spacer(modifier = Modifier.height(16.dp))

        // Upcoming Deliveries
        UpcomingDeliveries(navController)

        // Button to return to the home screen
        Button(
            onClick = { navController.navigate("home_screen") },
            modifier = Modifier

                .padding(16.dp)
        ) {
            Text("🏠 Back to Home")
        }
    }
}

// Composable for Delivery Stats Card
@Composable
fun StatsCard(totalDeliveries: Int, successfulDeliveries: Int, averageRating: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Delivery Stats", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Total Deliveries: $totalDeliveries")
            Text("Successful Deliveries: $successfulDeliveries")
            Text("Average Rating: $averageRating ★")
        }
    }
}

// Composable for Daily Challenges Card
@Composable
fun ChallengesCard(challengesCompleted: Int, totalChallenges: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Daily Challenges", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Completed: $challengesCompleted / $totalChallenges")
            LinearProgressIndicator(
                progress = challengesCompleted.toFloat() / totalChallenges,
                modifier = Modifier.fillMaxWidth().height(24.dp)
            )
        }
    }
}

// Composable for Rewards Card
@Composable
fun RewardsCard(rewards: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Rewards", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Points Earned: $rewards")
        }
    }
}

// Composable for Upcoming Deliveries
@Composable
fun UpcomingDeliveries(navController: NavHostController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Upcoming Deliveries", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            // Sample upcoming deliveries (this can be replaced with actual data)
            Text("1. Pizza - 123 Main St")
            Text("2. Sushi - 456 Elm St")
            Text("3. Salad - 789 Oak St")
        }
    }
}
