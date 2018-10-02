package com.kairias97.ezitineraries.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.model.CalculatedItinerary
import com.kairias97.ezitineraries.presenter.IItineraryDetailPresenter
import com.kairias97.ezitineraries.utils.DateUtil
import com.kairias97.ezitineraries.utils.toast
import com.kairias97.ezitineraries.view.IItineraryDetailView
import dagger.android.support.DaggerAppCompatActivity
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject

class ItineraryDetailActivity : DaggerAppCompatActivity(), IItineraryDetailView {


    private lateinit var mBtnNext: Button
    val EXTRA_CALCULATED_ITINERARY: String = "calculatedItinerary"
    val EXTRA_CALCULATED_ITINERARY_ID: String = "calculatedItineraryId"
    @Inject lateinit var  mItineraryDetailPresenter: IItineraryDetailPresenter


    private lateinit var mTextDestination: TextView
    private lateinit var mTextDate: TextView
    private lateinit var mTextCalculatedDistance: TextView
    private lateinit var mTextScore: TextView
    private lateinit var mTextMaxDistance: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itinerary_details)

        supportActionBar!!.title = getString(R.string.title_activity_itinerary_detail)



        mTextDestination = findViewById(R.id.textView_destination)
        mTextDate = findViewById(R.id.textView_date)
        mTextCalculatedDistance = findViewById(R.id.textView_calculated_distance)
        mTextScore = findViewById(R.id.textView_score)
        mTextMaxDistance = findViewById(R.id.textView_max_distance)

        mBtnNext = findViewById(R.id.button_next_activity)
        mItineraryDetailPresenter.takeView(this)
        mItineraryDetailPresenter.provideItinerary()
        mBtnNext.setOnClickListener {
            mItineraryDetailPresenter.onItineraryPreferencesVisualizationRequest()
        }
    }
    fun navigateToTripPlanAttractions(){
        val intent = Intent(this, TripPlanAttractionsActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        mItineraryDetailPresenter.takeView(this)
        super.onResume()
    }

    override fun onPause() {
        mItineraryDetailPresenter.dropView()
        super.onPause()
    }

    override fun onDestroy() {
        mItineraryDetailPresenter.dropView()
        super.onDestroy()
    }

    override fun navigateToItineraryPreferencesVisualization(calculatedItinerary: CalculatedItinerary, isNew: Boolean) {
        val intent = Intent(this, ItineraryPreferencesActivity::class.java)
        intent.putExtra("calculatedItinerary",calculatedItinerary)
        intent.putExtra("isNewItinerary",isNew)
        startActivity(intent)
    }

    override fun showItineraryInformation(itinerary: CalculatedItinerary) {
        val location = "${itinerary.city!!.name}, ${itinerary.city!!.country!!.name}"
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        supportActionBar!!.title = String.format(getString(R.string.placeholder_itinerary_title), location)
        mTextDestination.text = location
        mTextDate.text = DateUtil.parseDateToFormat(itinerary.visitDate!!, "dd/MM/yyyy")
        mTextCalculatedDistance.text = df.format(itinerary.calculatedKmDistance).toString()
        mTextScore.text = df.format(itinerary.score!!).toString()
        mTextMaxDistance.text = itinerary.restriction!!.maxDistance.toString()
    }

    override fun showCommunicationErrorMessage() {
        this.toast(R.string.generic_500_error)
    }
}
