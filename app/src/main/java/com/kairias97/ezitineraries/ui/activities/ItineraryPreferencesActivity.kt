package com.kairias97.ezitineraries.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.model.CalculatedItinerary
import com.kairias97.ezitineraries.model.ItineraryTouristPreference
import com.kairias97.ezitineraries.model.TouristTripPreference
import com.kairias97.ezitineraries.presenter.IItineraryPreferencesPresenter
import com.kairias97.ezitineraries.ui.adapters.ItineraryPreferenceAdapter
import com.kairias97.ezitineraries.ui.adapters.SavedPreferenceAdapter
import com.kairias97.ezitineraries.utils.toast
import com.kairias97.ezitineraries.view.IItineraryPreferencesView
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class ItineraryPreferencesActivity : DaggerAppCompatActivity(), IItineraryPreferencesView {


    val EXTRA_CALCULATED_ITINERARY: String = "calculatedItinerary"
    val EXTRA_IS_NEW_ITINERARY: String = "isNewItinerary"
    @Inject
    lateinit var  mItineraryPreferencesPresenter:  IItineraryPreferencesPresenter

    private lateinit var mRecycler: RecyclerView
    private lateinit var mButton: Button
    private lateinit var mAdapter: ItineraryPreferenceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itinerary_preferences)


        mRecycler = findViewById(R.id.recycler_preferences)
        mButton = findViewById(R.id.button_next_activity)

        mAdapter = ItineraryPreferenceAdapter(ArrayList<ItineraryTouristPreference>())
        mRecycler.adapter = mAdapter
        mRecycler.layoutManager = LinearLayoutManager(this)
        mRecycler.setHasFixedSize(true)

        mButton.setOnClickListener {
            mItineraryPreferencesPresenter.onItineraryAttractionsVisualizationRequest()
        }

        supportActionBar!!.title = getString(R.string.title_activity_itinerary_preferences)

        mItineraryPreferencesPresenter.takeView(this)
        mItineraryPreferencesPresenter.providePreferences()
    }

    override fun onResume() {
        mItineraryPreferencesPresenter.takeView(this)
        super.onResume()
    }

    override fun onPause() {
        mItineraryPreferencesPresenter.dropView()
        super.onPause()
    }

    override fun onDestroy() {
        mItineraryPreferencesPresenter.dropView()
        super.onDestroy()
    }

    override fun navigateToItineraryAttractionsVisualization(calculatedItinerary: CalculatedItinerary, isNew: Boolean) {
        val intent = Intent(this,ItineraryRouteActivity::class.java)
        intent.putExtra("calculatedItinerary",calculatedItinerary)
        intent.putExtra("isNewItinerary",isNew)
        startActivity(intent)
    }

    override fun showPreferencesUI(preferences: List<ItineraryTouristPreference>) {
        (mRecycler.adapter as ItineraryPreferenceAdapter).setPreferenceList(preferences.toMutableList())
        (mRecycler.adapter as ItineraryPreferenceAdapter).notifyDataSetChanged()
    }

    override fun showCommunicationErrorMessage() {
        this.toast(R.string.generic_500_error)
    }
}
