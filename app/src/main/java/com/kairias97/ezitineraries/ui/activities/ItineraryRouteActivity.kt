package com.kairias97.ezitineraries.ui.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Point
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.ui.IconGenerator
import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.model.CalculatedItinerary
import com.kairias97.ezitineraries.model.TouristAttraction
import com.kairias97.ezitineraries.model.TouristVisit
import com.kairias97.ezitineraries.presenter.IItineraryRoutePresenter
import com.kairias97.ezitineraries.ui.adapters.TouristAttractionInfoWindowGoogleMap
import com.kairias97.ezitineraries.utils.ColorUtil
import com.kairias97.ezitineraries.utils.DialogUtil
import com.kairias97.ezitineraries.utils.toast
import com.kairias97.ezitineraries.view.IItineraryRouteView
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class ItineraryRouteActivity : DaggerAppCompatActivity(), IItineraryRouteView, OnMapReadyCallback,
        GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnMyLocationButtonClickListener{


    val EXTRA_CALCULATED_ITINERARY: String = "calculatedItinerary"
    val EXTRA_IS_NEW_ITINERARY: String = "isNewItinerary"
    private lateinit var mMap: GoogleMap
    @Inject
    lateinit var mItineraryRoutePresenter : IItineraryRoutePresenter
    private val MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1000
    private lateinit var mButton: Button
    private lateinit var  mWebsiteLinearLayout : LinearLayout
    private lateinit var  mPhoneLinearLayout: LinearLayout
    private lateinit var  mBottomSheetLinearLayout: LinearLayout
    private lateinit var  mWebsiteTextView: TextView
    private lateinit var  mPhoneTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itinerary_route)

        mBottomSheetLinearLayout = findViewById(R.id.bottomSheet_info)
        mWebsiteLinearLayout = findViewById(R.id.linearLayout_website)
        mPhoneLinearLayout = findViewById(R.id.linearLayout_phone)
        mWebsiteTextView = findViewById(R.id.textView_website)
        mPhoneTextView = findViewById(R.id.textView_phone)
        mBottomSheetLinearLayout.visibility = View.GONE
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        mButton = findViewById(R.id.button_save)

        mButton.setOnClickListener {
            mItineraryRoutePresenter.onItinerarySaveRequest()
        }

        supportActionBar!!.title = getString(R.string.title_activity_itinerary_route)

        mButton.visibility = View.GONE

    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMarkerClickListener {
            val selected = it.tag
            var attractionSelected : TouristAttraction? = null
            if (selected is TouristAttraction) {
                attractionSelected = selected
            } else if (selected is TouristVisit) {
                attractionSelected = selected.touristAttraction
            }
            if (attractionSelected?.websiteUrl != null || attractionSelected?.phoneNumber != null) {

                mBottomSheetLinearLayout.visibility = View.VISIBLE
                attractionSelected?.websiteUrl?.let {
                    //mWebsiteLinearLayout.visibility = View.VISIBLE
                    mWebsiteTextView.text = it
                } ?: run {
                    mWebsiteTextView.text = "-"
                    //mWebsiteLinearLayout.visibility = View.GONE
                }
                attractionSelected?.phoneNumber?.let {
                    //mPhoneLinearLayout.visibility = View.VISIBLE
                    mPhoneTextView.text = it
                } ?: run {
                    mPhoneTextView.text = "-"
                    //mPhoneLinearLayout.visibility = View.GONE
                }
            } else {
                mBottomSheetLinearLayout.visibility = View.GONE
                mWebsiteTextView.text = "-"
                mPhoneTextView.text = "-"
            }
            false
        }
        mMap.setOnInfoWindowCloseListener {
            //mBottomSheetLinearLayout.visibility = View.GONE

            mBottomSheetLinearLayout.visibility = View.GONE
            mWebsiteTextView.text = "-"
            mPhoneTextView.text = "-"
        }
        mMap.setInfoWindowAdapter(TouristAttractionInfoWindowGoogleMap(this))
        mItineraryRoutePresenter.takeView(this)
        mItineraryRoutePresenter.provideTouristVisits()

    }

    private fun checkLocationPermission() {
        // For checking the location permission
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION)
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION)

            }
        } else {
            mMap?.isMyLocationEnabled = true
            mMap?.setOnMyLocationButtonClickListener(this)
            mMap?.setOnMyLocationClickListener(this)
            // Permission has already been granted
        }
    }
    override fun onMyLocationButtonClick(): Boolean {
        return false
    }

    override fun onMyLocationClick(location: Location) {


    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted
                    if (mMap != null) {
                        try {
                            mMap.isMyLocationEnabled = true
                            mMap.setOnMyLocationButtonClickListener(this)
                            mMap.setOnMyLocationClickListener(this)

                        } catch (ex : SecurityException) {
                        }
                    }
                } else {
                    //Do nothing here since the my location function was never enabled
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
    override fun onResume() {
        mItineraryRoutePresenter.takeView(this)
        super.onResume()
    }

    override fun onPause() {
        mItineraryRoutePresenter.dropView()
        super.onPause()
    }

    override fun onDestroy() {
        mItineraryRoutePresenter.dropView()
        super.onDestroy()
    }
    override fun showTouristAttractions(attractions: List<TouristAttraction>) {
        for(item in attractions){
            val local = LatLng(item.geolocation?.latitude!!,item.geolocation?.longitude!!)
            mMap.addMarker(MarkerOptions()
                    .position(local)
                    .icon(ColorUtil.getMarkerIcon("#1c94b6"))).tag = item
        }
    }

    override fun traceVisitRoutes(visits: List<TouristVisit>) {
        var pointsToConnect = visits.map {
            var latLng = LatLng(it.touristAttraction!!.geolocation!!.latitude!!,
                    it.touristAttraction!!.geolocation!!.longitude!!
                    )
            latLng
        }
        var poly: Polyline = mMap.addPolyline(PolylineOptions().clickable(true))
        poly.points = pointsToConnect.toList()
        poly.color = Color.argb(150,28,148,182)
        poly.width = 16.toFloat()

        //poly.endCap = CustomCap(BitmapDescriptorFactory.fromResource(R.drawable.ic_navigation_black_18dp), 100.toFloat())
        val iconGen = IconGenerator(this)
        iconGen.setStyle(IconGenerator.STYLE_GREEN)
        for((i, item) in visits.withIndex()){
            if (i != visits.count() -1) {
                val local = LatLng(item.touristAttraction?.geolocation?.latitude!!,item.touristAttraction?.geolocation?.longitude!!)
                //The colors are setup with de decimal conversion minus android value
                if(i==0){
                    //For green color
                    iconGen.setColor(-10177964)

                    //iconGen.setStyle(IconGenerator.STYLE_GREEN)
                } else{
                    //iconGen.setColor(R.color.colorPrimary - 16777216)
                    //For orange
                    iconGen.setColor(-810190)

                    //iconGen.setStyle(IconGenerator.STYLE_ORANGE)
                }
                val targetIcon = iconGen.makeIcon(item.visitOrder?.toString() ?: "-")
                mMap.addMarker(MarkerOptions()
                        .position(local)
                        .icon(BitmapDescriptorFactory.fromBitmap(targetIcon))
                        .anchor(iconGen.anchorU, iconGen.anchorV)).tag = item
            }

        }
        if (visits.count() > 0) {
            val location = LatLng(visits[visits.count() - 1].touristAttraction?.geolocation?.latitude!!, visits[visits.count() - 1].touristAttraction?.geolocation?.longitude!!)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15.toFloat()))
            /*mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.toFloat()))*/
        }
        //After tracing the whole route, checking the permissions to verify the gps access
        checkLocationPermission()


    }

    override fun navigateToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun showSavedItineraryMessage() {
        this.toast(R.string.message_saving_itinerary_successful)
    }

    override fun showItinerarySaveButton() {
        mButton.visibility = View.VISIBLE
    }

    override fun showItinerarySavingProgressMessage() {
        DialogUtil.showDialog(this,getString(R.string.message_saving_itinerary))
    }

    override fun hideItinerarySavingProgressMessage() {
        DialogUtil.hideDialog()
    }

    override fun showCommunicationErrorMessage() {
        this.toast(R.string.generic_500_error)
    }
}
