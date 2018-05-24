package com.example.ivan.secondareatest.example

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.ivan.secondareatest.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapViewActivity : AppCompatActivity(), OnMapReadyCallback {

    var mMap:GoogleMap? = null
    lateinit var map_view:MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_view)

        map_view = findViewById(R.id.map_view)
        map_view.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0

        val coord = LatLng(37.18,57.12)

        mMap?.addMarker(MarkerOptions()
                .position(coord)
                .title("test mark"))
        //mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(coord,15F))
    }
/*
    override fun onStart() {
        super.onStart()
        map_view.onStart()
    }

    override fun onResume() {
        super.onResume()
        map_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        map_view.onPause()
    }

    override fun onStop() {
        super.onStop()
        map_view.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        map_view.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map_view.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        map_view.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }*/
}
