package com.kairias97.ezitineraries.ui.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
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
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.model.TouristAttraction
import com.kairias97.ezitineraries.model.TripPlan
import com.kairias97.ezitineraries.presenter.ITripPlanAttractionsVisualizationPresenter
import com.kairias97.ezitineraries.ui.adapters.TouristAttractionInfoWindowGoogleMap
import com.kairias97.ezitineraries.utils.ColorUtil
import com.kairias97.ezitineraries.utils.DialogUtil
import com.kairias97.ezitineraries.utils.toast
import com.kairias97.ezitineraries.view.ITripPlanAttractionsVisualizationView
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class TripPlanAttractionsActivity : DaggerAppCompatActivity(), OnMapReadyCallback,
        ITripPlanAttractionsVisualizationView,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener{

    val EXTRA_TRIP_PLAN: String = "tripPlan"
    val EXTRA_IS_NEW_TRIP_PLAN: String = "isNewTripPlan"
    private lateinit var mMap: GoogleMap

    @Inject
    lateinit var mTripPlanAttractionsPresenter : ITripPlanAttractionsVisualizationPresenter
    private lateinit var mButton: Button

    private lateinit var  mWebsiteLinearLayout : LinearLayout
    private lateinit var  mPhoneLinearLayout: LinearLayout
    private lateinit var  mBottomSheetLinearLayout: LinearLayout
    private lateinit var  mWebsiteTextView: TextView
    private lateinit var  mPhoneTextView: TextView
    private val MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_plan_attractions)
        mButton = findViewById(R.id.button_save)
        mBottomSheetLinearLayout = findViewById(R.id.bottomSheet_info)
        mWebsiteLinearLayout = findViewById(R.id.linearLayout_website)
        mPhoneLinearLayout = findViewById(R.id.linearLayout_phone)
        mWebsiteTextView = findViewById(R.id.textView_website)
        mPhoneTextView = findViewById(R.id.textView_phone)
        //Setup sheet behavior
        mBottomSheetLinearLayout.visibility = View.GONE
        //mBottomSheetLinearLayout.visibility = View.GONE

        mButton.setOnClickListener {
            mTripPlanAttractionsPresenter.onTripPlanSaveConfirmation()
        }

        mButton.visibility = View.GONE

        supportActionBar!!.title = getString(R.string.title_activity_trip_plan_atractions)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMarkerClickListener {
            val attractionSelected = it.tag as TouristAttraction
            if (attractionSelected.websiteUrl != null || attractionSelected.phoneNumber != null) {

                mBottomSheetLinearLayout.visibility = View.VISIBLE
                attractionSelected.websiteUrl?.let {
                    //mWebsiteLinearLayout.visibility = View.VISIBLE
                    mWebsiteTextView.text = it
                } ?: run {
                    mWebsiteTextView.text = "-"
                    //mWebsiteLinearLayout.visibility = View.GONE
                }
                attractionSelected.phoneNumber?.let {
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
        mMap.setInfoWindowAdapter(TouristAttractionInfoWindowGoogleMap(this))
        mMap.setOnInfoWindowCloseListener {
            //mBottomSheetLinearLayout.visibility = View.GONE

            mBottomSheetLinearLayout.visibility = View.GONE
            mWebsiteTextView.text = "-"
            mPhoneTextView.text = "-"
        }
        mTripPlanAttractionsPresenter.takeView(this)
        mTripPlanAttractionsPresenter.provideTouristAttractions()


    }


    override fun onMyLocationButtonClick(): Boolean {
        return false
    }

    override fun onMyLocationClick(location: Location) {
        //Nothing necessary at the moment
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
        mTripPlanAttractionsPresenter.takeView(this)
        super.onResume()
    }

    override fun onPause() {
        mTripPlanAttractionsPresenter.dropView()
        super.onPause()
    }

    override fun onDestroy() {
        mTripPlanAttractionsPresenter.dropView()
        super.onDestroy()
    }

    override fun showSaveTripPlanButton() {
        mButton.visibility = View.VISIBLE
    }

    override fun showTouristAttractionsUI(attractions: List<TouristAttraction>) {
        for(item in attractions){
            val local = LatLng(item.geolocation?.latitude!!,item.geolocation?.longitude!!)
            mMap.addMarker(MarkerOptions()
                    .position(local)
                    .icon(ColorUtil.getMarkerIcon("#1c94b6"))).tag = item
        }
        if (attractions.count() > 0) {
            val location = LatLng(attractions[attractions.count() - 1].geolocation?.latitude!!, attractions[attractions.count() -1].geolocation?.longitude!!)
            /*mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.toFloat()))*/
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15.toFloat()))
        }
        //Verify after setting the attractions, the permission of gps location
        checkLocationPermission()

    }

    override fun navigateToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun showSavedTripPlanSuccessMessage() {
        this.toast(R.string.message_saving_trip_plan_successful)
    }

    override fun showTripPlanSavingProgressMessage() {
        DialogUtil.showDialog(this, getString(R.string.message_saving_trip_plan))
    }

    override fun hideTripPlanSavingProgressMessage() {
        DialogUtil.hideDialog()
    }

    override fun showCommunicationErrorMessage() {
        this.toast(R.string.generic_500_error)
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
            mMap.isMyLocationEnabled = true
            // Permission has already been granted
            mMap.setOnMyLocationButtonClickListener(this)
            mMap.setOnMyLocationClickListener(this)
        }
    }
}
