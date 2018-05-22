package com.example.ivan.secondareatest.example

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.ivan.secondareatest.R


import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.SphericalUtil
import kotlinx.android.synthetic.main.activity_km_show_maps.*

class KmShowMapsActivity : AppCompatActivity(), OnMapReadyCallback,
    View.OnClickListener, GoogleMap.OnMapClickListener{

    private lateinit var mMap: GoogleMap
    private var listCoordinates:ArrayList<LatLng> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_km_show_maps)

        cancel_button.setOnClickListener(this)
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
        cancel_button.isClickable = true
        mMap.setOnMapClickListener(this)
    }

    override fun onClick(v: View?) {
        mMap.clear()
        listCoordinates = ArrayList()
    }

    override fun onMapClick(p0: LatLng?) {
        if (listCoordinates.size < 2){
            listCoordinates.add(p0!!)

            drawPolyline()
        }
    }

    private fun drawPolyline(){
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(listCoordinates[0]).title("A"))


        if (listCoordinates.size>1){
            val mark = mMap.addMarker(MarkerOptions().position(
                    listCoordinates[1])
                    .title("${SphericalUtil.computeDistanceBetween(listCoordinates[0],listCoordinates[1]).toInt()} km"))//distanceInKmBetweenEarthCoordinates()
            mark.showInfoWindow()
        }
        val polyline = mMap.addPolyline(PolylineOptions()
                .clickable(true)
                .addAll(listCoordinates))

        // Store a data object with the polyline, used here to indicate an arbitrary type.
        polyline.tag = "Path"
    }

    private fun distanceInKmBetweenEarthCoordinates(coordinate1:LatLng,coordinate2:LatLng): Double{
        val earthRadiisInLm = 6371
        val dLat = Math.toRadians(coordinate2.latitude - coordinate1.latitude)
        val dLon = Math.toRadians(coordinate2.longitude - coordinate1.longitude)

        val lat1 = Math.toRadians(coordinate1.latitude)
        val lat2 = Math.toRadians(coordinate2.latitude)

        var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a))
        return earthRadiisInLm*c
    }

    fun degreesToRadians(number:Double) = number*Math.PI/180
}
