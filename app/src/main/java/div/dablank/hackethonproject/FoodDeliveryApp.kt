package div.dablank.hackethonproject

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun FoodDeliveryApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main_screen") {
        composable("main_screen") { MainScreen(navController) }
        composable("delivery_screen") { DeliveryScreen(navController) }
        composable("leaderboard_screen") { LeaderboardScreen(navController) }
    }
}
