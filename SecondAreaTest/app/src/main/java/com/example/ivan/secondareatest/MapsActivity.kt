package com.example.ivan.secondareatest

import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.PatternItem
import java.util.*
import android.widget.Toast


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
GoogleMap.OnPolylineClickListener,GoogleMap.OnPolygonClickListener,
        GoogleMap.OnMapClickListener,GoogleMap.OnPoiClickListener,
GoogleMap.OnMyLocationClickListener{
    override fun onMapClick(p0: LatLng?) {

        Log.d("Test","onMapClick - "+getAddressString(p0))
    }

    private fun getAddressString(latlng:LatLng?):String{
        val geocoder= Geocoder(this,Locale.getDefault())
        val address:List<Address>

        address = geocoder.getFromLocation(latlng!!.latitude,latlng!!.longitude,1)
        var returnString = "adress = ${address[0].getAddressLine(0)}, " +
                "city = ${address[0].locality}, " +
                "state = ${address[0].adminArea}, " +
                "country = ${address[0].countryName}, " +
                "postalCode = ${address[0].postalCode}, " +
                "knownName = ${address[0].featureName}"
        return returnString
    }

    override fun onPoiClick(p0: PointOfInterest?) {
        Log.d("Test","onPoiClick")
    }

    override fun onMyLocationClick(p0: Location) {
        Log.d("Test","onMyLocationClick")
    }

    companion object {
        private const val COLOR_BLACK_ARGB = 0xff000000
        private const val COLOR_WHITE_ARGB = 0xffffffff
        private const val COLOR_GREEN_ARGB = 0xff388E3C
        private const val COLOR_PURPLE_ARGB = 0xff81C784
        private const val COLOR_ORANGE_ARGB = 0xffF57F17
        private const val COLOR_BLUE_ARGB = 0xffF9A825

        private val POLYLINE_STROKE_WIDTH_PX = 12
        private val POLYGON_STROKE_WIDTH_PX = 8
        private val PATTERN_DASH_LENGTH_PX = 20
        private val PATTERN_GAP_LENGTH_PX = 20

        private val  DOT:PatternItem = Dot()
        private val DASH:PatternItem = Dash(PATTERN_DASH_LENGTH_PX.toFloat())
        private val GAP:PatternItem = Gap(PATTERN_GAP_LENGTH_PX.toFloat())

        // Create a stroke pattern of a gap followed by a dot.
        private val PATTERN_POLYLINE_DOTTED = Arrays.asList(GAP, DOT)

        // Create a stroke pattern of a gap followed by a dash.
        private val PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH)

        // Create a stroke pattern of a dot followed by a gap, a dash, and another gap.
        private val PATTERN_POLYGON_BETA = Arrays.asList(DOT, GAP, DASH, GAP)


    }

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
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

        addMarkers()
        addPolyline()
        addPolygone()

        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-23.684,133.903), 4F))

        // Set listeners for click events.
        mMap.setOnPolylineClickListener(this)
        mMap.setOnPolygonClickListener(this)
        mMap.setOnMapClickListener(this)
        mMap.setOnMyLocationClickListener(this)


    }

    private fun addMarkers(){
        val ufa = LatLng(54.7,55.9)
        val spb = LatLng(59.9,30.1)
        mMap.addMarker(MarkerOptions().position(ufa).title("This is UFA"))
        mMap.addMarker(MarkerOptions().position(spb).title("This is SPB"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(ufa))
    }

    private fun addPolyline(){
        // Add polylines to the map.
        // Polylines are useful to show a route or some other connection between points.
        val polyline = mMap.addPolyline(PolylineOptions()
                .clickable(true)
                .add(LatLng(-35.016, 143.321),
                     LatLng(-34.747, 145.592),
                     LatLng(-34.364, 147.891),
                     LatLng(-33.501, 150.217),
                     LatLng(-32.306, 149.248),
                     LatLng(-32.491, 147.309)))

        // Store a data object with the polyline, used here to indicate an arbitrary type.
        polyline.tag = "A"

        stylePolyline(polyline)

        val polyline2 = mMap.addPolyline(PolylineOptions()
                .clickable(true)
                .add(LatLng(-29.501, 119.700),
                     LatLng(-27.456, 119.672),
                     LatLng(-25.971, 124.187),
                     LatLng(-28.081, 126.555),
                     LatLng(-28.848, 124.229),
                     LatLng(-28.215, 123.938)))

        polyline2.tag = "B"

        stylePolyline(polyline2)

        val polyline3 = mMap.addPolyline(PolylineOptions()
                .clickable(true)
                .add(
                        LatLng(54.7,55.9),
                        LatLng(59.9,30.1)

                ))

        polyline3.tag = "B"

        stylePolyline(polyline3)
    }

    /**
     * Styles the polyline, based on type.
     * @param polyline The polyline object that needs styling.
     */
    private fun stylePolyline(polyline: Polyline) {
        var type = ""

        //Get the data object stored with the polyline
        if(polyline.tag != null){
            type = polyline.tag.toString()
        }

        when(type){
        // If no type is given, allow the API to use the default.
            "A"->{
                polyline.startCap = RoundCap()
            }
            "B"->{
                //Use a round cap at the start of the line
                polyline.startCap = RoundCap()
            }
        }

        polyline?.endCap = RoundCap()
        polyline?.width = POLYLINE_STROKE_WIDTH_PX.toFloat()
        polyline?.color = COLOR_BLACK_ARGB.toInt()
        polyline?.jointType = JointType.ROUND
    }

    private fun addPolygone() {
        // Add polygons to indicate areas on the map.
        val polygone1 = mMap.addPolygon(PolygonOptions()
                .clickable(true)
                .add(
                        LatLng(-27.457, 153.040),
                        LatLng(-33.852, 151.211),
                        LatLng(-37.813, 144.962),
                        LatLng(-34.928, 138.599)
                ))

        // Store a data object with the polygon, used here to indicate an arbitrary type.

        polygone1.tag = "alpha"
        //Style the polygone
        stylePolygone(polygone1)

        val polygone2 = mMap.addPolygon(PolygonOptions()
                .clickable(true)
                .add(
                        LatLng(-31.673, 128.892),
                        LatLng(-31.952, 115.857),
                        LatLng(-17.785, 122.258),
                        LatLng(-12.4258, 130.7932)
                ))

        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygone2.tag = "beta"
        //Style the polygone
        stylePolygone(polygone2)
    }

    private fun stylePolygone(polygone1: Polygon?) {
        var type = ""

        //Get the data object stored with the polyline
        if(polygone1?.tag != null){
            type = polygone1.tag.toString()
        }

        var pattern: List<PatternItem>? = null
        var strokeColor = COLOR_BLACK_ARGB
        var fillColor = COLOR_WHITE_ARGB

        when (type) {
        // If no type is given, allow the API to use the default.
            "alpha" -> {
                // Apply a stroke pattern to render a dashed line, and define colors.
                pattern = PATTERN_POLYGON_ALPHA
                strokeColor = COLOR_GREEN_ARGB
                fillColor = COLOR_PURPLE_ARGB
            }
            "beta" -> {
                // Apply a stroke pattern to render a line of dots and dashes, and define colors.
                pattern = PATTERN_POLYGON_BETA
                strokeColor = COLOR_ORANGE_ARGB
                fillColor = COLOR_BLUE_ARGB
            }
        }

        polygone1?.strokePattern = pattern
        polygone1?.strokeWidth = POLYGON_STROKE_WIDTH_PX.toFloat()
        polygone1?.strokeColor = strokeColor.toInt()
        polygone1?.fillColor = fillColor.toInt()
    }

    /**
     * Listens for clicks on a polyline.
     * @param polyline The polyline object that the user has clicked.
     */
    override fun onPolylineClick(p0: Polyline?) {
        // Flip from solid stroke to dotted stroke pattern.
        if ((p0?.pattern == null) || (!p0.pattern!!.contains(DOT))) {
            p0?.pattern = PATTERN_POLYLINE_DOTTED
        } else {
            // The default pattern is a solid stroke.
            p0.pattern = null
        }

        Toast.makeText(this, "Route type " + p0?.tag.toString(),
                Toast.LENGTH_SHORT).show()

    }

    /**
     * Listens for clicks on a polygon.
     * @param polygon The polygon object that the user has clicked.
     */
    override fun onPolygonClick(p0: Polygon) {
        // Flip the values of the red, green, and blue components of the polygon's color.
        var color = p0.strokeColor xor 0x00ffffff
        p0.strokeColor = color
        color = p0.fillColor xor 0x00ffffff
        p0.fillColor = color

        Toast.makeText(this, "Area type " + p0.tag.toString(), Toast.LENGTH_SHORT).show()

    }

}
