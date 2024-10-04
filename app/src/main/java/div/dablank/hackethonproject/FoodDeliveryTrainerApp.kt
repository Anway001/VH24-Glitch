package div.dablank.hackethonproject

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import div.dablank.myproject.LocationViewModel

@Composable
fun FoodDeliveryTrainerApp() {
    val navController = rememberNavController()
    val context = LocalContext.current // Get the context here
    val viewModel = remember { LocationViewModel() } // Create ViewModel here
    val locationUtils = remember { LocationUtils(context) } // Create LocationUtils here with context

    NavHost(navController = navController, startDestination = "home_screen") {
        composable("home_screen") {
            HomeScreen(navController) // Update HomeScreen to include navigation to location selection
        }
        composable("locationscreen") {
            LocationSelectionScreen(navController, viewModel, locationUtils) { newLocation ->
                // Handle the selected location data here
                viewModel.updateLocation(newLocation) // Update the ViewModel with the new location
                navController.navigate("game_screen") // Navigate to the game screen
            }
        }
        composable("game_screen") {
            val address = "123 Main St" // This can be dynamically set based on the location selection
            GameScreen(
                navController = navController,
                locationUtils = locationUtils,
                viewModel = viewModel,
                context = context, // Pass context to GameScreen
                address = address
            )
        }
        composable("leaderboard") { LeaderboardScreen(navController) }
    }
}
