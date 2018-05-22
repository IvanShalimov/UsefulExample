package com.example.ivan.secondareatest.example

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.ivan.secondareatest.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.activity_sneak_map.*

class SneakMapActivity : AppCompatActivity(),
        OnMapReadyCallback,GoogleMap.OnMapClickListener, View.OnClickListener {


    private lateinit var mMap: GoogleMap
    private var listCoordinates:ArrayList<LatLng> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sneak_map)

        clear_button.setOnClickListener(this)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        clear_button.isClickable = true
        mMap.setOnMapClickListener(this)
    }

    override fun onMapClick(p0: LatLng?) {
        listCoordinates.add(p0!!)
        drawPolyline()
    }

    private fun drawPolyline(){
        mMap.clear()
        val polyline = mMap.addPolyline(PolylineOptions()
                .clickable(true)
                .addAll(listCoordinates))
        /*LatLng(-35.016, 143.321),
                        LatLng(-34.747, 145.592),
                        LatLng(-34.364, 147.891),
                        LatLng(-33.501, 150.217),
                        LatLng(-32.306, 149.248),
                        LatLng(-32.491, 147.309)*/

        // Store a data object with the polyline, used here to indicate an arbitrary type.
        polyline.tag = "Path"
    }

    override fun onClick(v: View?) {
        listCoordinates = ArrayList()
        mMap.clear()
    }
}
