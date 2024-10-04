package div.dablank.hackethonproject

import LocationSelectionScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.LatLng
import div.dablank.hackethonproject.DeliveryGameScreen
import div.dablank.hackethonproject.LeaderboardScreen

import div.dablank.hackethonproject.MainScreen

@Composable
fun FoodDeliveryApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main_screen") {
        composable("main_screen") { MainScreen(navController) }
        composable("location_selection_screen") {
            LocationSelectionScreen(navController) { selectedLocation ->
                navController.navigate("delivery_screen/${selectedLocation.latitude},${selectedLocation.longitude}")
            }
        }
        composable("delivery_screen/{startingLocation}") { backStackEntry ->
            val startingLocation = backStackEntry.arguments?.getString("startingLocation")?.split(",")?.let {
                LatLng(it[0].toDouble(), it[1].toDouble())
            } ?: LatLng(19.0760, 72.8777) // Default location
            DeliveryGameScreen(navController, startingLocation)
        }
        composable("leaderboard_screen") { LeaderboardScreen(navController) }
    }
}
