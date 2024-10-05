package div.dablank.hackethonproject

import androidx.compose.animation.core.animateFloatAsState
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
                LeaderboardPlayerRow(player)
                Spacer(modifier = Modifier.height(8.dp)) // Space between rows
            }
        }
    }
}

@Composable
fun LeaderboardPlayerRow(player: Player) {
    val animatedScore by animateFloatAsState(targetValue = player.score.toFloat())

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(2.dp, Color(0xFF90CAF9), RoundedCornerShape(10.dp))
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clickable { /* Navigate to player profile or details */ },
        color = if (player.score > 100) Color(0xFFFFE082) else Color.White // Gold for top players
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Player Rank
                Text(
                    text = "${player.score}.",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                    modifier = Modifier.width(30.dp),
                    color = if (player.score > 100) Color(0xFFFFB300) else Color.Gray // Gold for top players
                )

                // Player Name
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                    modifier = Modifier.weight(1f)
                )

                // Player Score with animation
                Text(
                    text = "${animatedScore.toInt()}",
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF388E3C)),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            // Custom Score Bar
            CustomScoreBar(score = player.score, maxScore = 150)
        }
    }
}

@Composable
fun CustomScoreBar(score: Int, maxScore: Int) {
    // Calculate the width based on the score
    val progress = score.toFloat() / maxScore

    // Custom Score Bar using Box
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(Color(0xFFD1C4E9), RoundedCornerShape(4.dp)) // Background color
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress) // Set width based on score progress
                .background(Color(0xFF388E3C), RoundedCornerShape(4.dp)) // Progress color
        )
    }
}

// Data class for Player
data class Player(
    val name: String,
    val score: Int
)
