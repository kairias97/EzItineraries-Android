package com.kairias97.ezitineraries.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.model.TripPlan
import com.kairias97.ezitineraries.presenter.ITripPlanDetailPresenter
import com.kairias97.ezitineraries.utils.DateUtil
import com.kairias97.ezitineraries.utils.toast
import com.kairias97.ezitineraries.view.ITripPlanDetailView
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class TripPlanDetailActivity : DaggerAppCompatActivity(), ITripPlanDetailView {
    @Inject
    lateinit var mTripPlanDetailPresenter : ITripPlanDetailPresenter


    private lateinit var mTextDestination: TextView
    private lateinit var mTextDate: TextView
    private lateinit var mTextTripName: TextView
    private lateinit var mTextDescription: TextView
    private lateinit var mTextDistance: TextView

    private lateinit var mButton: Button

    val EXTRA_TRIP_PLAN_ID: String = "tripPlanId"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_plan_details)

        mTextDestination = findViewById(R.id.textView_destination)
        mTextDate = findViewById(R.id.textView_date)
        mTextTripName = findViewById(R.id.textView_trip_name)
        mTextDescription = findViewById(R.id.textView_trip_description)
        mTextDistance = findViewById(R.id.textView_max_distance)

        mButton = findViewById(R.id.button_next_activity)
        mButton.setOnClickListener {
            mTripPlanDetailPresenter.onPreferencesVisualizationRequest()
        }

        supportActionBar!!.title = "Plan de viaje"

        mTripPlanDetailPresenter.takeView(this)
        mTripPlanDetailPresenter.provideTripPlan()
    }

    override fun onResume() {
        mTripPlanDetailPresenter.takeView(this)
        super.onResume()
    }

    override fun onPause() {
        mTripPlanDetailPresenter.dropView()
        super.onPause()
    }

    override fun onDestroy() {
        mTripPlanDetailPresenter.dropView()
        super.onDestroy()
    }
    override fun showTripPlanInformation(tripPlan: TripPlan) {
        val location = "${tripPlan.city!!.name}, ${tripPlan.city!!.country!!.name}"
        supportActionBar!!.title = String.format(getString(R.string.placeholder_trip_plan_title),
                location)
        mTextDestination.text = location
        mTextDate.text = DateUtil.parseDateToFormat(tripPlan.date!!,"dd/MM/yyyy")
        mTextTripName.text = tripPlan.name
        mTextDescription.text = tripPlan.description
        mTextDistance.text = tripPlan.restriction!!.maxDistance.toString()
    }

    override fun navigateToTripPlanPreferencesVisualization(tripPlan: TripPlan) {
        val intent = Intent(this,TripPlanSavedPreferencesActivity::class.java)
        intent.putExtra("tripPlan",tripPlan)
        startActivity(intent)
    }

    override fun showCommunicationErrorMessage() {
       this.toast(R.string.generic_500_error)
    }

}
