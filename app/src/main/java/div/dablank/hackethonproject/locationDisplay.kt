package div.dablank.hackethonproject

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import div.dablank.myproject.LocationViewModel

@Composable
fun LocationDisplayScreen(navController: NavHostController) {
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)
    val viewModel: LocationViewModel = viewModel()

    LocationDisplay(locationUtils = locationUtils, viewModel = viewModel, context = context, navController)
}

@Composable
fun LocationDisplay(
    locationUtils: LocationUtils,
    viewModel: LocationViewModel,
    context: Context,
    navController: NavHostController
) {
    val location = viewModel.location.value
    val address = location?.let { locationUtils.reverseGeocodeLocation(it) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                locationUtils.requestLocationUpdates(viewModel)
            } else {
                Toast.makeText(context, "Location Permission is required", Toast.LENGTH_LONG).show()
            }
        }
    )

    // Animated floating effect for the welcome text
    val animatedScale by animateFloatAsState(targetValue = 1.2f, animationSpec = tween(800),
        label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFB3E5FC), Color(0xFFE1F5FE))))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Fun title with gradient text and animation
        Text(
            text = "🚴‍♂️ Welcome to the\nFood Delivery Trainer!",
            style = MaterialTheme.typography.titleLarge.copy(color = Color(0xFF0D47A1)),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
                .graphicsLayer(scaleX = animatedScale, scaleY = animatedScale)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Add an icon/image with a playful drop shadow and tilt effect
        Image(
            painter = painterResource(id = R.drawable.parth),
            contentDescription = "Delivery Icon",
            modifier = Modifier
                .size(120.dp)
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .padding(12.dp)
                .graphicsLayer(rotationZ = 6f) // Add slight rotation for a fun look
                .shadow(8.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Location information section with sleek card-like appearance
        if (location != null) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(Color(0xFF81D4FA)),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🌍 Your Location:",
                        style = MaterialTheme.typography.titleSmall.copy(color = Color(0xFF0D47A1)),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Latitude: ${location.latitude}\nLongitude: ${location.longitude}\nAddress: $address",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            Text(
                text = "📍 Location not available",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Call-to-action button with a creative style
        Button(
            onClick = {
                if (locationUtils.hasLocationPermission(context)) {
                    locationUtils.requestLocationUpdates(viewModel)
                } else {
                    requestPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
            },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853)),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .shadow(8.dp, shape = RoundedCornerShape(50))
        ) {
            Text(text = "🌍 Get My Location", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Back to home button with a vibrant color
        Button(
            onClick = { navController.navigate("home_screen") },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF5350)),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .shadow(8.dp, shape = RoundedCornerShape(50))
        ) {
            Text(text = "🏠 Back to Home", color = Color.White)
        }
    }
}
