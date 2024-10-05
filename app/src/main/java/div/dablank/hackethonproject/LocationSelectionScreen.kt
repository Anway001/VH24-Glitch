package div.dablank.hackethonproject

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import div.dablank.myproject.LocationViewModel


@Composable
fun LocationSelectionScreen(
    navController: NavHostController,
    locationViewModel: LocationViewModel,
    locationUtils: LocationUtils,
    onLocationSelected: (LocationData) -> Unit
) {
    val userLocation = remember { mutableStateOf(LatLng(0.0, 0.0)) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation.value, 10f)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.weight(1f).padding(top = 16.dp),
            cameraPositionState = cameraPositionState,
            onMapClick = { userLocation.value = it }
        ) {
            Marker(state = MarkerState(position = userLocation.value))
        }

        Button(onClick = {
            val newLocation = LocationData(userLocation.value.latitude, userLocation.value.longitude)
            onLocationSelected(newLocation) // Update ViewModel and navigate back
            locationUtils.requestLocationUpdates(locationViewModel) // Start location updates
            navController.navigate("game_screen") // Navigate to the game screen
        }) {
            Text("Set Location")
        }
    }
}

