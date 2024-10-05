package div.dablank.myproject

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import div.dablank.hackethonproject.GeocodingResult
import div.dablank.hackethonproject.LocationData

import kotlinx.coroutines.launch

import androidx.compose.runtime.mutableStateOf

class LocationViewModel : ViewModel() {
    var location = mutableStateOf<LocationData?>(null)
        private set

    fun setLocation(newLocation: LocationData) {
        location.value = newLocation
    }
}
