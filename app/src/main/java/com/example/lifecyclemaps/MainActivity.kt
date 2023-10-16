package com.example.lifecyclemaps

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    lateinit var observer: LocationLifecycleObserver

    val stringViewModel: LocationViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observer =  LocationLifecycleObserver(this, stringViewModel)
        requestPermissions()

        // You need to create the lifecycle observer and add it to the lifecycle

        this.lifecycle.addObserver(observer)

    }

    fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
        } else {
            // Set "gpsPermission" to true in the lifecycle observer
             observer.gpsPermission = true

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 0 && grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Set "gpsPermission" to true in the lifecycle observer
            observer.gpsPermission = true
        } else {
            AlertDialog.Builder(this).setPositiveButton("OK", null).setMessage("GPS permission denied").show()
            // Set "gpsPermission" to false in the lifecycle observer
            observer.gpsPermission = false
        }
    }
}
