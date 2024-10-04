import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LocationSelectionScreen(navController: NavController, onLocationSelected: (LatLng) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Select Starting Location") })
        },
        content = {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Choose Your Starting Location", style = MaterialTheme.typography.headlineSmall)

                // Define the available locations
                val locations = listOf(
                    LatLng(19.0760, 72.8777),  // Mumbai
                    LatLng(18.5204, 73.8567),  // Pune
                    LatLng(19.9975, 73.7898)   // Nashik
                )

                locations.forEach { location ->
                    Button(onClick = {
                        onLocationSelected(location)
                        navController.navigate("delivery_screen/${location.latitude},${location.longitude}") // Navigate to the game screen
                    }) {
                        Text(text = "Start from ${location.latitude}, ${location.longitude}")
                    }
                }
            }
        }
    )
}
