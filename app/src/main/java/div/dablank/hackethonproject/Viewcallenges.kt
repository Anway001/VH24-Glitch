package div.dablank.hackethonproject

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ViewChallengesScreen(navController: NavHostController) {
    // Sample challenges data
    val challenges = listOf(
        Challenge("Speed Challenge", "Deliver 5 packages in under 10 minutes."),
        Challenge("Fuel Efficiency Challenge", "Complete 3 deliveries using less than 30% fuel."),
        Challenge("Traffic Navigation Challenge", "Complete deliveries while avoiding traffic alerts.")
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
                color = Color.White // Change text color for visibility
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(challenges) { challenge ->
                    ChallengeCard(challenge) {
                        // Navigate to challenge detail or start challenge
                        navController.navigate("challenge_detail/${challenge.id}")
                    }
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
data class Challenge(val title: String, val description: String, val id: Int = (0..100).random())

// Composable for Challenge Card
@Composable
fun ChallengeCard(challenge: Challenge, onClick: () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize(), // Smooth expansion and collapse
        colors = CardDefaults.cardColors(
            containerColor = if (isExpanded) Color(0xFFE3F2FD) else Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .clickable {
                    isExpanded = !isExpanded // Toggle expansion
                    onClick()
                }
        ) {
            Text(text = challenge.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = challenge.description, style = MaterialTheme.typography.bodyMedium)
            if (isExpanded) {
                // Additional details can be added here
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Details about this challenge can go here!", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
