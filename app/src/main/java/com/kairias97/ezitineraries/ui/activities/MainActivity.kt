package com.kairias97.ezitineraries.ui.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import com.crashlytics.android.Crashlytics
import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.ui.fragments.ItinerariesFragment
import com.kairias97.ezitineraries.ui.fragments.SuggestAttractionFragment
import com.kairias97.ezitineraries.ui.fragments.TripPlansFragment
import dagger.android.support.DaggerAppCompatActivity
import io.fabric.sdk.android.Fabric
import javax.inject.Inject
import javax.inject.Named

class MainActivity : DaggerAppCompatActivity() {
    private lateinit var mNavigation: BottomNavigationView
    @Inject lateinit var mSuggestionFragment: SuggestAttractionFragment
    @Inject lateinit var mTripPlansFragment: TripPlansFragment
    @Inject lateinit var mItinerariesFragment: ItinerariesFragment



    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var fragment: Fragment = Fragment()
        when (item.itemId) {
            R.id.navigation_trip_plans -> {
                fragment = mTripPlansFragment as Fragment
            }
            R.id.navigation_itineraries -> {
                fragment = mItinerariesFragment as Fragment
            }
            R.id.navigation_attraction_suggestion -> {
                fragment  = mSuggestionFragment as Fragment
            }
        }
        fragmentTransaction(fragment)
        true
    }
    private fun fragmentTransaction(fragment: android.support.v4.app.Fragment){
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_fragments, fragment)
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mNavigation = findViewById(R.id.navigation)

        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        var fragment : Fragment = mTripPlansFragment as Fragment
        fragmentTransaction(fragment)
    }
}
