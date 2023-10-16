package com.example.lifecyclemaps

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView


class LocationEntryFrag : Fragment(R.layout.locationentryfrag) {
    val viewModel : LocationViewModel by activityViewModels()

    // Implement onViewCreated() to:
    // - listen for button click events
    // - read the location the user entered
    // - send the location to the method in the ViewModel which calls the web API to
    //   find its lat/lon and updates the LiveData appropriately
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<Button>(R.id.btnGo).setOnClickListener{
            val EnteredName = view.findViewById<EditText>(R.id.etPlace).text.toString()
            viewModel.SearchPlace(EnteredName)
        }
    }

}
