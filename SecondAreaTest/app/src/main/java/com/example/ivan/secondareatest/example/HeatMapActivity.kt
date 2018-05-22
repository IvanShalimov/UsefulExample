package com.example.ivan.secondareatest.example

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ivan.secondareatest.R

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.TileOverlay
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.maps.android.heatmaps.HeatmapTileProvider
import org.json.JSONArray
import org.json.JSONException
import java.util.*


class HeatMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heat_map)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        addHeatMap()
    }

    lateinit var mOverlay: TileOverlay

    private fun addHeatMap(){
        val list:List<LatLng>

        try {
            list = readItems(R.raw.police_stations)
            // Create a heat map tile provider, passing it the latlngs of the police stations.
            val mProvider = HeatmapTileProvider.Builder().data(list).build()

            mOverlay = mMap.addTileOverlay(TileOverlayOptions().tileProvider(mProvider))
        } catch (e:JSONException){
            Toast.makeText(this, "Problem reading list of locations.", Toast.LENGTH_LONG).show();
        }


    }

    @Throws(JSONException::class)
    private fun readItems(resource: Int): ArrayList<LatLng> {
        val list = ArrayList<LatLng>()
        val inputStream = resources.openRawResource(resource)
        val json = Scanner(inputStream).useDelimiter("\\A").next()
        val array = JSONArray(json)
        for (i in 0 until array.length()) {
            val `object` = array.getJSONObject(i)
            val lat = `object`.getDouble("lat")
            val lng = `object`.getDouble("lng")
            list.add(LatLng(lat, lng))
        }
        return list
    }

}
