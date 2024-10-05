package div.dablank.hackethonproject

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

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
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, fontSize = 28.sp),
            modifier = Modifier.padding(16.dp),
            color = Color(0xFF1976D2) // Dark blue
        )

        Spacer(modifier = Modifier.height(8.dp))

        // LazyColumn for the leaderboard entries
        LazyColumn {
            items(leaderboardData.sortedByDescending { it.score }) { player ->
                LeaderboardPlayerCard(player)
                Spacer(modifier = Modifier.height(8.dp)) // Space between cards
            }
        }
    }
}

@Composable
fun LeaderboardPlayerCard(player: Player) {
    val rank = calculateRank(player)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(5.dp, RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (player.score > 100) Color(0xFFFFE082) else Color(0xFFFAFAFA)
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
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp
                ),
                color = getMedalColor(rank)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Player Name
            Text(
                text = player.name,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, fontSize = 24.sp),
                color = Color(0xFF1976D2)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Player Score
            Text(
                text = "Score: ${player.score}",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                color = Color(0xFF388E3C)
            )
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
        else -> ""
    }
}

// Helper function to get the medal color
fun getMedalColor(rank: Int): Color {
    return when (rank) {
        1 -> Color(0xFFFFD700) // Gold
        2 -> Color(0xFFC0C0C0) // Silver
        3 -> Color(0xFFCD7F32) // Bronze
        else -> Color.Transparent
    }
}

// Helper function to calculate rank based on score
fun calculateRank(player: Player): Int {
    // This function could be improved to calculate actual rank based on player's score in the leaderboard
    return when (player.score) {
        150 -> 1
        120 -> 2
        100 -> 3
        else -> 4
    }
}
