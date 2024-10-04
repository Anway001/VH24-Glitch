package div.dablank.hackethonproject

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun LeaderboardScreen(navController: NavHostController) {
    val leaderboardData = listOf(
        Player("Anway", 12, R.drawable.baseline1),
        Player("Parth", 15, R.drawable.baseline_person_24),
        Player("Onkar", 10, R.drawable.baseline1),
        Player("Divyanshu", 18, R.drawable.baseline_person_24),
        Player("Jay", 13, R.drawable.baseline1)
    )

    val sortedLeaderboard = leaderboardData.sortedBy { it.time } // Sort by time

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Title with custom styling
        Text(
            text = "🏆 Leaderboard",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
            modifier = Modifier.padding(16.dp)
        )

        // Player list with enhanced visual effects
        LazyColumn {
            items(sortedLeaderboard.size) { index ->
                val player = sortedLeaderboard[index]
                val isTopRank = index < 3 // Highlight top 3 players
                LeaderboardPlayerRow(player, rank = index + 1, isTopRank)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button with extra flare
        Button(
            onClick = { navController.navigate("home_screen") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(8.dp, RoundedCornerShape(12.dp))
        ) {
            Text(text = "🏠 Back to Home", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
        }
    }
}

@Composable
fun LeaderboardPlayerRow(player: Player, rank: Int, isTopRank: Boolean) {
    // Fun rank emojis
    val badgeEmoji = when (rank) {
        1 -> "🥇"
        2 -> "🥈"
        3 -> "🥉"
        else -> "🎖️"
    }

    // Dynamic gradient backgrounds for top ranks
    val backgroundColor = when (rank) {
        1 -> Brush.horizontalGradient(colors = listOf(Color(0xFFFFD700), Color(0xFFFFF176))) // Gold for first
        2 -> Brush.horizontalGradient(colors = listOf(Color(0xFFC0C0C0), Color(0xFFE0E0E0))) // Silver for second
        3 -> Brush.horizontalGradient(colors = listOf(Color(0xFFCD7F32), Color(0xFFDD9B6C))) // Bronze for third
        else -> Brush.horizontalGradient(colors = listOf(Color.LightGray, Color.White))
    }

    AnimatedVisibility(
        visible = true,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(backgroundColor)
                .padding(16.dp)
                .shadow(4.dp, RoundedCornerShape(12.dp))
        ) {
            // Profile picture with shadow for 3D effect
            Image(
                painter = painterResource(id = player.profilePicture),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .shadow(8.dp, CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Player details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "$badgeEmoji ${player.name}",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "⏱️ Delivery Time: ${player.time} minutes",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Rank with extra boldness
            Text(
                text = "#${rank}",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                modifier = Modifier.align(Alignment.CenterVertically),
                color = Color.Black
            )
        }
    }
}

// Data class for Player
data class Player(
    val name: String,
    val time: Int, // Delivery time in minutes
    val profilePicture: Int // Profile picture resource ID
)
