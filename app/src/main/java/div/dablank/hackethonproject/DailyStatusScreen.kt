package div.dablank.hackethonproject
import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.navigation.NavHostController
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun DailyStatusScreen(navController: NavHostController) {
    // Sample daily status data
    var deliveriesMade by remember { mutableStateOf(0) }
    var challengesCompleted by remember { mutableStateOf(2) }
    var rewardsEarned by remember { mutableStateOf(50) } // Example reward points

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF3F4F6)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "📅 Daily Status",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF3B82F6)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Displaying daily stats
        Text(text = "Deliveries Made: $deliveriesMade", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Challenges Completed: $challengesCompleted", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Rewards Earned: $rewardsEarned points", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Button to reset daily status or navigate back to the main menu
        Button(onClick = { navController.navigate("main_menu") }) {
            Text(text = "Go Back to Main Menu")
        }
    }
}
