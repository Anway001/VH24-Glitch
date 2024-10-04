package div.dablank.hackethonproject

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryScreen(navController: NavController) {
    var selectedRoute by remember { mutableStateOf<String?>(null) }
    var progress by remember { mutableStateOf(0f) }
    var timeTaken by remember { mutableStateOf(0) }
    var trafficEvent by remember { mutableStateOf<String?>(null) }

    val trafficEvents = listOf("Traffic Jam!", "Roadblock!", "Clear Road", "Heavy Traffic!")

    LaunchedEffect(selectedRoute) {
        if (selectedRoute != null) {
            while (progress < 1f) {
                delay(500)
                progress += 0.1f
                timeTaken += 1

                if ((1..100).random() < 20) {
                    trafficEvent = trafficEvents.random()
                }
            }
            navController.popBackStack() // Simulate completion
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Delivery Task") })
        },
        content = {
            Column(modifier = Modifier.padding(16.dp)) {
                if (selectedRoute == null) {
                    Text(text = "Select Your Route", style = MaterialTheme.typography.headlineMedium)

                    Button(onClick = { selectedRoute = "Route A" }) {
                        Text("Choose Route A - 15 mins")
                    }
                    Button(onClick = { selectedRoute = "Route B" }) {
                        Text("Choose Route B - 10 mins")
                    }
                    Button(onClick = { selectedRoute = "Route C" }) {
                        Text("Choose Route C - 12 mins")
                    }
                } else {
                    // Simulate delivery with moving icon
                    Text(text = "Delivering via $selectedRoute...", style = MaterialTheme.typography.headlineSmall)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        // Animate a delivery truck/bike icon moving horizontally as progress increases
                        val iconOffsetX = with(LocalDensity.current) { (progress * 200).toDp() }
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_directions_bike_24), // Replace with your custom bike/truck icon
                            contentDescription = "Delivery Vehicle",
                            modifier = Modifier.offset(x = iconOffsetX).size(64.dp)
                        )
                    }

                    if (trafficEvent != null) {
                        Text(text = "Traffic Event: $trafficEvent", color = Color.Red, modifier = Modifier.padding(8.dp))
                    }

                    Text(text = "Time Elapsed: $timeTaken seconds", style = MaterialTheme.typography.bodyLarge)
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.popBackStack() }) {
                Text("Back")
            }
        }
    )
}
