package div.dablank.hackethonproject

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Urban Delivery Training") })
        },
        content = {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "City Map", modifier = Modifier.padding(16.dp))
                // Dummy city map can be displayed here

                Button(onClick = { navController.navigate("delivery_screen") }) {
                    Text("Start Delivery Task")
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("leaderboard_screen") }) {
                Text("Leaderboard")
            }
        }
    )
}
