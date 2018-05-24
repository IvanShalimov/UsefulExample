package com.example.ivan.secondareatest.example

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ivan.secondareatest.R

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.util.*
import com.google.android.gms.maps.model.LatLng


class CircleRotateMapActivity : AppCompatActivity(), OnMapReadyCallback,
        GoogleMap.OnMapClickListener{


    private lateinit var mMap: GoogleMap
    lateinit var mark:Marker
    private var coordSave:LatLng? = null

    private val POLYLINE_STROKE_WIDTH_PX = 12
    private val POLYGON_STROKE_WIDTH_PX = 8
    private val PATTERN_DASH_LENGTH_PX = 20
    private val PATTERN_GAP_LENGTH_PX = 20

    private val  DOT:PatternItem = Dot()
    private val DASH:PatternItem = Dash(PATTERN_DASH_LENGTH_PX.toFloat())
    private val GAP:PatternItem = Gap(PATTERN_GAP_LENGTH_PX.toFloat())

    // Create a stroke pattern of a gap followed by a dot.
    private val PATTERN_POLYLINE_DOTTED = Arrays.asList(GAP, DOT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_rotate_map)

        if((savedInstanceState != null) && savedInstanceState.containsKey("key")) Log.d("Test","key exists")
        if(savedInstanceState != null)
            coordSave = savedInstanceState.getParcelable("key")

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map_view) as SupportMapFragment
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
        Log.d("Test","onMapReady")
        mMap = googleMap

        restoreIntent()
        mMap.setOnMapClickListener(this)
    }

    private fun restoreIntent(){
        if(coordSave != null){
            Log.d("Test","coord != null")
            mark = mMap.addMarker(MarkerOptions().position(coordSave!!).title("You're here!"))
        } else {
            Log.d("Test","null")
        }

    }

    override fun onMapClick(p0: LatLng?) {
        mMap.clear()
        coordSave = p0!!
        mark = mMap.addMarker(MarkerOptions().position(p0).title("You're here!"))

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(p0))
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelable("key",mark.position)
        super.onSaveInstanceState(outState)
    }
}
