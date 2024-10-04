package div.dablank.myproject

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import div.dablank.hackethonproject.GeocodingResult
import div.dablank.hackethonproject.LocationData
import div.dablank.hackethonproject.RetrofitClient
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {
    // State to hold the current location
    private val _location = mutableStateOf<LocationData?>(null)
    val location: State<LocationData?> = _location

    // State to hold the list of addresses from geocoding results
    private val _address = mutableStateOf(listOf<GeocodingResult>())
    val address: State<List<GeocodingResult>> = _address

    // Method to update the current location
    fun updateLocation(newLocation: LocationData) {
        _location.value = newLocation
    }

    // Method to fetch the address from coordinates
    fun fetchAddress(latlng: String) {
        try {
            viewModelScope.launch {
                // Replace with your Retrofit API call
                val result = RetrofitClient.create().getAddressFromCoordinates(
                    latlng,
                    "AIzaSyCnfwyKH4V8CE5XR38GGg_uZysFyHIXeZU"
                )
                _address.value = result.results // Update the address state with results
            }
        } catch (e: Exception) {
            Log.d("LocationViewModel", "${e.cause} ${e.message}") // Log any errors
        }
    }
}
