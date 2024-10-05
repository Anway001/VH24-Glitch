package div.dablank.hackethonproject

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class PlayerViewModel : ViewModel() {
    // List to hold player data
    val players = mutableStateListOf<PlayerData>()

    fun addPlayer(player: PlayerData) {
        players.add(player)
    }

    fun updatePlayer(name: String, timeTaken: Int, deliveriesMade: Int, fuelLeft: Float) {
        val player = players.find { it.name == name }
        player?.let {
            it.timeTaken = timeTaken
            it.deliveriesMade = deliveriesMade
            it.fuelLeft = fuelLeft
        }
    }

    // Function to get leaderboard sorted by time taken
    fun getLeaderboard(): List<PlayerData> {
        return players.sortedBy { it.timeTaken }
    }
}
