package div.dablank.hackethonproject

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerNameInputScreen(navController: NavHostController) {
    var playerName by remember { mutableStateOf("") } // State for player's name

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF3F4F6))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter Your Name",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF3B82F6)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = playerName,
            onValueChange = { playerName = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Blue,
                unfocusedIndicatorColor = Color.Gray,

            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (playerName.isNotBlank()) {
                    // Navigate to the game screen with the player's name
                    navController.navigate("game_screen") {
                        launchSingleTop = true
                    }
                } else {
                    Toast.makeText(navController.context, "Please enter a name", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Start Game")
        }
    }
}
