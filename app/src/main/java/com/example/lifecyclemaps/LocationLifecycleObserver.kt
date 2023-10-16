package com.example.lifecyclemaps

import android.app.AlertDialog
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import org.osmdroid.util.GeoPoint


class LocationLifecycleObserver(val ctx: Context, val vm: LocationViewModel) : DefaultLifecycleObserver,
    LocationListener {

    lateinit var locationManager: LocationManager
    var gpsPermission = false // has GPS permission been obtained yet?

    // Initialise your LocationManager in onCreate()
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        locationManager = ctx.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    }

    // Start requesting updates in onResume(), if GPS permission has been granted
    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        if (gpsPermission == true) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0f, this)
        }
        else {
            AlertDialog.Builder(ctx).setPositiveButton("OK", null)
                .setMessage("GPS will not work as you have denied access").show()
        }
    }

    // Remove updates (locationManager.removeUpdates(this)) in onPause(), if GPS permission has been granted
    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        locationManager.removeUpdates(this)
    }

    // Implement onLocationChanged() to update the LatLon in the ViewModel,
    // plus the other methods of LocationListener
    override fun onLocationChanged(loc: Location) {
        vm.latlon = LatLon(loc.latitude, loc.longitude)
    }
    override fun onProviderEnabled(provider: String) {
    }
    override fun onProviderDisabled(provider: String) {
    }
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }
}