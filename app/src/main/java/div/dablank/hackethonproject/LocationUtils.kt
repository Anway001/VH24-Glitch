package div.dablank.hackethonproject

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import div.dablank.myproject.LocationViewModel
import java.util.Locale

class LocationUtils(private val context: Context) {
    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private lateinit var locationCallback: LocationCallback

    // Method to check if location permissions are granted
    fun hasLocationPermission(context: Context): Boolean {
        return ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // Method to request location updates
    fun requestLocationUpdates(viewModel: LocationViewModel) {
        if (hasLocationPermission(context)) {
            val locationRequest = LocationRequest.create().apply {
                interval = 5000 // Update interval in milliseconds
                fastestInterval = 2000 // Fastest update interval
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }

            // Initialize the location callback
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    for (location in locationResult.locations) {
                        // Update the ViewModel with the new location
                        viewModel.updateLocation(LocationData(location.latitude, location.longitude))
                    }
                }
            }

            // Request location updates
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
            } else {
                // Handle lack of permissions (e.g., show a dialog to the user)
            }
        } else {
            // Handle lack of permissions (e.g., show a dialog to the user)
        }
    }

    // Method to reverse geocode location
// Method to reverse geocode location
    fun reverseGeocodeLocation(locationData: LocationData): String? {
        val geocoder = Geocoder(context, Locale.getDefault())
        return try {
            val addresses = geocoder.getFromLocation(locationData.latitude, locationData.longitude, 1)
            if (addresses?.isNotEmpty() == true) {
                addresses[0].getAddressLine(0) // Return the first address line
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null // Return null if there’s an exception
        }
    }


    // Method to remove location updates
    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}
