package com.kairias97.ezitineraries.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.model.TouristTripPreference
import com.kairias97.ezitineraries.model.TripPlan
import com.kairias97.ezitineraries.presenter.ITripPlanSavedPreferencesPresenter
import com.kairias97.ezitineraries.ui.adapters.SavedPreferenceAdapter
import com.kairias97.ezitineraries.utils.toast
import com.kairias97.ezitineraries.view.ITripPlanSavedPreferencesView
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class TripPlanSavedPreferencesActivity : DaggerAppCompatActivity(), ITripPlanSavedPreferencesView {

    val EXTRA_TRIP_PLAN: String = "tripPlan"
    @Inject
    lateinit var mTripPlanSavedPreferencesPresenter: ITripPlanSavedPreferencesPresenter

    private lateinit var mRecycler: RecyclerView
    private lateinit var mButton: Button
    private lateinit var mSavedPreferencesAdapter: SavedPreferenceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_plan_saved_preferences)

        supportActionBar!!.title = getString(R.string.title_activity_saved_preferences)
        mButton = findViewById(R.id.button_next_activity)

        mRecycler = findViewById(R.id.recycler_preferences)
        mSavedPreferencesAdapter = SavedPreferenceAdapter(ArrayList<TouristTripPreference>())
        mRecycler.adapter = mSavedPreferencesAdapter
        mRecycler.layoutManager = LinearLayoutManager(this)
        mRecycler.setHasFixedSize(true)

        mButton.setOnClickListener {
            mTripPlanSavedPreferencesPresenter.onAttractionsVisualizationRequest()
        }

        mTripPlanSavedPreferencesPresenter.takeView(this)
        mTripPlanSavedPreferencesPresenter.providePreferences()
    }

    override fun onResume() {
        mTripPlanSavedPreferencesPresenter.takeView(this)
        super.onResume()
    }

    override fun onPause() {
        mTripPlanSavedPreferencesPresenter.dropView()
        super.onPause()
    }

    override fun onDestroy() {
        mTripPlanSavedPreferencesPresenter.dropView()
        super.onDestroy()
    }

    override fun showTripPlanPreferencesUI(preferences: List<TouristTripPreference>) {
        (mRecycler.adapter  as SavedPreferenceAdapter).setPreferenceList(preferences.toMutableList())
        (mRecycler.adapter as SavedPreferenceAdapter).notifyDataSetChanged()
    }

    override fun navigateToTripPlanVisualization(tripPlan: TripPlan) {
        val intent = Intent(this, TripPlanAttractionsActivity::class.java)
        intent.putExtra("tripPlan",tripPlan)
        intent.putExtra("isNewTripPlan",false)
        startActivity(intent)
    }

    override fun showCommunicationErrorMessage() {
        this.toast(R.string.generic_500_error)
    }
}
