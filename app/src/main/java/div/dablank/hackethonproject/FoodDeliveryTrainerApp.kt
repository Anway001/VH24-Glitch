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
    val context = LocalContext.current
    val viewModel = remember { LocationViewModel() }
    val locationUtils = remember { LocationUtils(context) }

    NavHost(navController = navController, startDestination = "home_screen") {
        composable("home_screen") {
            HomeScreen(navController)
        }
        composable("player_name_input") {
            PlayerNameInputScreen(navController)
        }

        composable("locationscreen") {
            LocationSelectionScreen(navController, viewModel, locationUtils) { newLocation ->
                navController.navigate("location_overview")
            }
        }

        // New Location Overview Screen added
        composable("location_overview") {
            LocationOverviewScreen(navController)
        }

        composable("game_screen") {
            val employeeName = "Player" // Default value for employee name
            GameScreen(
                navController = navController,
                locationUtils = locationUtils,
                viewModel = viewModel,
                context = context,
                employeeName = employeeName // Update this to the entered name later
            )
        }

        composable("leaderboard") {
            LeaderboardScreen(navController)
        }

        // New challenges and daily status screens added
        composable("view_challenges") {
            ViewChallengesScreen(navController)
        }

        composable("daily_status") {
            DailyStatusScreen(navController)
        }

        // New Animation Practice Screen added
        composable("animation_practice") {
            AnimationPracticeScreen(navController)
        }

        // New challenge detail screen added
        composable("challenge_detail/{challengeId}") { backStackEntry ->
            val challengeId = backStackEntry.arguments?.getString("challengeId")
            ChallengeDetailScreen(challengeId)
        }
    }
}
