package div.dablank.hackethonproject

import android.content.Context
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import div.dablank.myproject.LocationViewModel
import android.os.Looper
import com.google.android.gms.location.*


class LocationUtils(private val context: Context) {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    val location = mutableStateOf<LocationData?>(null)

    fun hasLocationPermission(context: Context): Boolean {
        return ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    fun requestLocationUpdates(viewModel: LocationViewModel) {
        if (hasLocationPermission(context)) {
            fusedLocationClient.requestLocationUpdates(
                LocationRequest.create().apply {
                    interval = 10000 // Update every 10 seconds
                    fastestInterval = 5000 // Fastest update every 5 seconds
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                },
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        super.onLocationResult(locationResult)
                        for (location in locationResult.locations) {
                            // Update ViewModel with the new location
                            viewModel.setLocation(LocationData(location.latitude, location.longitude))
                            this@LocationUtils.location.value = LocationData(location.latitude, location.longitude)
                        }
                    }
                },
                Looper.getMainLooper()
            )
        } else {
            // Consider handling permission requests here
            // e.g. requestPermissions() if necessary
        }
    }

    suspend fun reverseGeocodeLocation(location: LocationData): String {
        // Make API call to fetch address from coordinates
        val apiKey = "YOUR_API_KEY" // Replace with your actual API key
        val latlng = "${location.latitude},${location.longitude}"

        return try {
            val response = RetrofitInstance.geocodingApiService.getAddressFromCoordinates(latlng, apiKey)
            if (response.status == "OK") {
                response.results.firstOrNull()?.formatted_address ?: "Address not found"
            } else {
                "Error: ${response.status}"
            }
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }

    // Add a method to stop location updates when needed
    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(object : LocationCallback() {})
    }
}
