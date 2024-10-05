package div.dablank.hackethonproject

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlin.math.absoluteValue

@Composable
fun LeaderboardScreen(navController: NavHostController) {
    // Sample leaderboard data
    val leaderboardData = remember {
        listOf(
            Player(name = "Alice", score = 150),
            Player(name = "Bob", score = 120),
            Player(name = "Charlie", score = 100),
            Player(name = "David", score = 90),
            Player(name = "Eva", score = 80),
            Player(name = "Frank", score = 70),
            Player(name = "Grace", score = 60),
            Player(name = "Heidi", score = 50),
            Player(name = "Ivan", score = 40),
            Player(name = "Judy", score = 30)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFBBDEFB), Color(0xFF90CAF9)),
                    startY = 0f,
                    endY = 1000f
                )
            )
            .padding(16.dp)
    ) {
        Text(
            text = "🏆 Leaderboard",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, fontSize = 32.sp),
            modifier = Modifier.padding(16.dp),
            color = Color(0xFF0D47A1) // Deep Blue
        )

        Spacer(modifier = Modifier.height(8.dp))

        // LazyColumn for the leaderboard entries
        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(leaderboardData.sortedByDescending { it.score }) { player ->
                LeaderboardPlayerCard(player)
            }
        }
    }
}

@Composable
fun LeaderboardPlayerCard(player: Player) {
    val rank = calculateRank(player)

    // Slightly animated scaling effect for each card
    val animatedScale by remember {
        mutableStateOf(1f + (player.score % 10) / 100f)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .shadow(8.dp, RoundedCornerShape(20.dp))
            .graphicsLayer {
                scaleX = animatedScale
                scaleY = animatedScale
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (player.score > 100) Color(0xFFFFE082) else Color(0xFFF1F8E9)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Medal based on rank
            val medal = getMedal(rank)
            Text(
                text = medal,
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp
                ),
                color = getMedalColor(rank)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Player Name
            Text(
                text = player.name,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    letterSpacing = 1.2.sp
                ),
                color = Color(0xFF1B5E20) // Dark Green
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Player Score with icon
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star, // Star icon for score
                    contentDescription = null,
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${player.score}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    color = Color(0xFF4CAF50) // Bright Green for score
                )
            }
        }
    }
}

// Data class for Player
data class Player(
    val name: String,
    val score: Int
)

// Helper function to get the medal based on rank
fun getMedal(rank: Int): String {
    return when (rank) {
        1 -> "🥇"
        2 -> "🥈"
        3 -> "🥉"
        else -> "🎖️" // Participation medal for others
    }
}

// Helper function to get the medal color
fun getMedalColor(rank: Int): Color {
    return when (rank) {
        1 -> Color(0xFFFFD700) // Gold
        2 -> Color(0xFFC0C0C0) // Silver
        3 -> Color(0xFFCD7F32) // Bronze
        else -> Color(0xFF64B5F6) // Light Blue for others
    }
}

// Helper function to calculate rank based on score
fun calculateRank(player: Player): Int {
    return when (player.score) {
        150 -> 1
        120 -> 2
        100 -> 3
        else -> 4
    }
}
