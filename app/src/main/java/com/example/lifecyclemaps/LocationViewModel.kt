package com.example.lifecyclemaps


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray

// You'll find these imports useful for the network communication and JSON parsing
import org.json.JSONObject
import java.net.URL

data class LatLon(var lat: Double=51.05, var lon: Double=-0.72)

class LocationViewModel : ViewModel(){
    // Create a latLon property (of type LatLon) and corresponding LiveData, as last week
    var latlon: LatLon = LatLon()
        set(value) {
            field = value
            livelatlon.value = value
        }
    var livelatlon = MutableLiveData<LatLon>()



    // Write a method to search for a given place by name
    // This should use a coroutine and:
    // - call https://hikar.org/webapp/map/search?q=<the name the user entered>
    // - parse the JSON returned. It's one object containing "lat" and "lon" fields
    // - set the latLon property to be a LatLon object containing the data from the JSON
    fun SearchPlace (s:String){
        Log.d("networkcomm", "Searching for: $s")
        viewModelScope.launch {
            var httpResponse = ""
            withContext(Dispatchers.IO) {
                Log.d("networkcomm", "sending url to server")
                httpResponse = URL("https://opentrailview.org/nomproxy.php?q=$s").readText()
                Log.d("networkcomm", "done")
            }
            Log.d("networkcomm",  "HTTP response = $httpResponse")
            var parsedData = ""
            val jsonArray = JSONArray(httpResponse) // 'json' contains our JSON (see above)

             if (jsonArray.length() > 0 ) {
                 val curObj = jsonArray.getJSONObject(0)


                 val lat = curObj.getString("lat").toDouble()
                 val lon = curObj.getString("lon").toDouble()

                 latlon.lat = lat
                 latlon.lon = lon
             }


        }

    }
}
