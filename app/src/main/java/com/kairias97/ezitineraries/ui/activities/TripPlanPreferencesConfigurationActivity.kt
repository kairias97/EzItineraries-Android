package com.kairias97.ezitineraries.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.LinearLayout
import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.model.Category
import com.kairias97.ezitineraries.model.TouristTripPreference
import com.kairias97.ezitineraries.model.TripPlan
import com.kairias97.ezitineraries.presenter.ITripPlanPreferencesConfigurationPresenter
import com.kairias97.ezitineraries.ui.adapters.ConfigurePreferenceAdapter
import com.kairias97.ezitineraries.utils.DialogUtil
import com.kairias97.ezitineraries.utils.toast
import com.kairias97.ezitineraries.view.ITripPlanPreferencesConfigurationView
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class TripPlanPreferencesConfigurationActivity : DaggerAppCompatActivity(), ITripPlanPreferencesConfigurationView {

    private lateinit var mBtnNext: Button

    val EXTRA_TRIP_PLAN: String = "tripPlan"
    @Inject
    lateinit var mTripPlanPreferencesConfigurationPresenter: ITripPlanPreferencesConfigurationPresenter


    private lateinit var mRecycler: RecyclerView
    private lateinit var mPreferencesAdapter: ConfigurePreferenceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configure_preferences_trip_plan)
        supportActionBar!!.title = getString(R.string.title_activity_preferences_configuration)


        mTripPlanPreferencesConfigurationPresenter.takeView(this)

        mBtnNext = findViewById(R.id.button_next_activity)
        mRecycler = findViewById(R.id.recycler_preferences)

        mPreferencesAdapter = ConfigurePreferenceAdapter(ArrayList<TouristTripPreference>())
        mRecycler.adapter = mPreferencesAdapter
        mRecycler.layoutManager = LinearLayoutManager(this)
        mRecycler.setHasFixedSize(true)
        mBtnNext.setOnClickListener {
            val list = (mRecycler.adapter  as ConfigurePreferenceAdapter).getPreferencesList()
            mTripPlanPreferencesConfigurationPresenter.onNextStepRequest(list)
        }
        mTripPlanPreferencesConfigurationPresenter.provideCategoryPreferences()
    }

    override fun onResume() {
        mTripPlanPreferencesConfigurationPresenter.takeView(this)
        super.onResume()
    }

    override fun onPause() {
        mTripPlanPreferencesConfigurationPresenter.dropView()
        super.onPause()
    }

    override fun onDestroy() {
        mTripPlanPreferencesConfigurationPresenter.dropView()
        super.onDestroy()
    }

    override fun showCategoryPreferencesListUI(preferences: List<TouristTripPreference>) {
        (mRecycler.adapter as ConfigurePreferenceAdapter).setConfigurePreferencesList(preferences.toMutableList())
        (mRecycler.adapter as ConfigurePreferenceAdapter).notifyDataSetChanged()
    }

    override fun showCategoriesLoadProgressMessage() {
        DialogUtil.showDialog(this,getString(R.string.message_loading_categories))
    }

    override fun hideCategoriesLoadProgressMessage() {
        DialogUtil.hideDialog()
    }

    override fun showInvalidWeightsMessage() {
        this.toast(R.string.message_invalid_weights)
    }

    override fun navigateToLastStep(tripPlan: TripPlan) {
        val intent = Intent(this,
                TripPlanAttractionsActivity::class.java)
        intent.putExtra("tripPlan",tripPlan)
        intent.putExtra("isNewTripPlan",true)
        startActivity(intent)
    }

    override fun showCommunicationErrorMessage() {
        this.toast(R.string.generic_500_error)
    }
}
