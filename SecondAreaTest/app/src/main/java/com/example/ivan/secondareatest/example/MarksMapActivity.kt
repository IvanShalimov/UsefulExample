package com.example.ivan.secondareatest.example

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.example.ivan.secondareatest.R
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MarksMapActivity : AppCompatActivity(), OnMapReadyCallback ,
        LocationListener,GoogleMap.OnMapClickListener {

    private lateinit var mMap: GoogleMap
    private val PERMISSION_CODE = 1

    lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Test","onCreate")

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        setContentView(R.layout.activity_marks_map)
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
        mMap.setOnMapClickListener(this)
        Log.d("Test","onMapReady")

        // Instantiates a new CircleOptions object and defines the center and radius
        val circleOptions = CircleOptions()
                .center(LatLng(37.4, -122.1))
                .radius(100000.0) // In meters

        // Get back the mutable Circle
        val circle = mMap.addCircle(circleOptions)

        registryLocationListener()
    }

    private fun registryLocationListener(){
        Log.d("Test","registryLocationListener")
        if (checkPermission()) {
            Log.d("Test","permission is granted")
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0,
                    0F,
                    this)
        } else {
            Log.d("Test","permission is denied")
            ActivityCompat.requestPermissions(this, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                    ),PERMISSION_CODE)
        }

    }

    private fun checkPermission() =
            (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED && (
                    ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED
                    ))


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("Test","onRequestPermissionResult")
        when(requestCode){

            PERMISSION_CODE -> {
                Log.d("Test","PERMISSION_CODE")
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Log.d("Test","1")
                    registryLocationListener()
                } else {
                    Log.d("Test","grantResults[0] = ${grantResults[0]}")
                }
                return

            }
        }
    }

    lateinit var mark:Marker

    override fun onLocationChanged(location: Location?) {
        Log.d("Test","onLocationChanged")
        mMap.clear()
        //vector is conflict
        val bd:BitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)

        val currentLocation = LatLng(location!!.latitude,location!!.longitude)
        mMap.addMarker(MarkerOptions()
                .position(currentLocation)
                .snippet("Population: 4,137,400")
                .title("You are here!").icon(bd))
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {

    }

    override fun onDestroy() {
        super.onDestroy()
        locationManager.removeUpdates(this)
    }

    override fun onMapClick(p0: LatLng?) {
        Log.d("Test","onMapClick")
        mMap.clear()
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p0, 20F))
        mark = mMap.addMarker(MarkerOptions().position(p0!!).title("Marks!"))
    }
}
