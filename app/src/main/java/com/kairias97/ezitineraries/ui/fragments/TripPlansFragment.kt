package com.kairias97.ezitineraries.ui.fragments

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.di.ActivityScoped
import com.kairias97.ezitineraries.model.CalculatedItinerary
import com.kairias97.ezitineraries.model.TripPlanHeader
import com.kairias97.ezitineraries.presenter.ITripPlansAdminPresenter
import com.kairias97.ezitineraries.ui.activities.ItineraryDetailActivity
import com.kairias97.ezitineraries.ui.activities.NewTripPlanActivity
import com.kairias97.ezitineraries.ui.activities.TripPlanDetailActivity
import com.kairias97.ezitineraries.ui.adapters.TripPlanAdapter
import com.kairias97.ezitineraries.utils.DialogUtil
import com.kairias97.ezitineraries.utils.toast
import com.kairias97.ezitineraries.view.ITripPlansAdminView
import dagger.android.support.DaggerFragment
import javax.inject.Inject

@ActivityScoped
class TripPlansFragment @Inject constructor() : DaggerFragment(), ITripPlansAdminView, TripPlanAdapter.OnTripPlanSelectedListener {

    private lateinit var mFabBtn: FloatingActionButton

    @Inject lateinit var mTripPlansAdminPresenter : ITripPlansAdminPresenter

    private lateinit var mTripPlansAdapter: TripPlanAdapter

    private lateinit var mRecycler: RecyclerView

    private lateinit var mTextView: TextView

    protected var mProgressDialog: ProgressDialog? = null

    private lateinit var mImageSee: ImageView
    private lateinit var mTextSee: TextView
    private lateinit var mImageCreate: ImageView
    private lateinit var mTextCreate: TextView
    private lateinit var mImageDelete: ImageView
    private lateinit var mTextDelete: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        activity.title = getString(R.string.title_fragment_trip_plans)
        val view = inflater.inflate(R.layout.fragment_trip_plans, container, false)
        mRecycler = view.findViewById(R.id.recycler_trip_plans)
        mTextView = view.findViewById(R.id.textView_label_no_products)
        mTripPlansAdapter = TripPlanAdapter(ArrayList<TripPlanHeader>(), this)
        mRecycler.adapter = mTripPlansAdapter
        mRecycler.layoutManager = LinearLayoutManager(activity)
        mRecycler.setHasFixedSize(true)
        mFabBtn = view.findViewById(R.id.fab_add_plan)
        mFabBtn.setOnClickListener {
            mTripPlansAdminPresenter.onNewTripPlanRequest()
        }
        mTripPlansAdminPresenter.takeView(this)
        mTripPlansAdminPresenter.provideTripPlanHeaders()

        return view
    }

    fun navigateToNewPlan(){
        val intent = Intent(activity, NewTripPlanActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        mTripPlansAdminPresenter.takeView(this)
        super.onResume()
    }

    override fun onDetach() {
        mTripPlansAdminPresenter.dropView()
        super.onDetach()

    }

    override fun onDestroy() {
        mTripPlansAdminPresenter.dropView()
        super.onDestroy()
    }

    override fun navigateToNewTripPlanDestinationScreen() {
        val intent = Intent(activity, NewTripPlanActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToViewTripPlanDestinationScreen(tripPlanId: Int) {
        val intent = Intent(activity, TripPlanDetailActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_NO_HISTORY
        intent.putExtra("tripPlanId",tripPlanId)
        startActivity(intent)
    }

    override fun removeTripPlanHeaderFromUI(tripPlanHeader: TripPlanHeader) {
        (mRecycler.adapter as TripPlanAdapter).removeTripPlan(tripPlanHeader)
        (mRecycler.adapter as TripPlanAdapter).notifyDataSetChanged()
    }

    override fun showTripPlanHeaders(tripPlanHeaders: List<TripPlanHeader>) {
        if(tripPlanHeaders.count()>0){
            (mRecycler.adapter as? TripPlanAdapter)!!.setTripPlansList(tripPlanHeaders.toMutableList())
            (mRecycler.adapter as? TripPlanAdapter)!!.notifyDataSetChanged()
            mTextView.visibility = View.GONE
        }
        else{
            mTextView.visibility = View.VISIBLE
        }
    }

    override fun showItineraryRequestProgressMessage() {
        DialogUtil.showDialog(activity, getString(R.string.message_creating_itinerary))
    }

    override fun hideItineraryRequestProgressMessage() {
        DialogUtil.hideDialog()
    }

    override fun navigateToViewCalculatedItineraryScreen(calculatedItinerary: CalculatedItinerary) {
        val intent = Intent(activity, ItineraryDetailActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_NO_HISTORY
        intent.putExtra("calculatedItinerary",calculatedItinerary)
        intent.putExtra("calculatedItineraryId",calculatedItinerary.city!!.cityId)
        startActivity(intent)
    }

    override fun showCommunicationErrorMessage() {
        activity.toast(R.string.generic_500_error)
    }

    override fun onTripPlanSelected(tripPlan: TripPlanHeader) {
        val dialog = BottomSheetDialog(activity)
        val view = layoutInflater.inflate(R.layout.menu_trip_plans, null)
        mImageSee = view.findViewById(R.id.image_see_trip_plan_details)
        mTextSee = view.findViewById(R.id.see_trip_plan_details)
        mImageCreate = view.findViewById(R.id.image_generate_itinerary_proposal)
        mTextCreate = view.findViewById(R.id.generate_itinerary_proposal)
        mImageDelete = view.findViewById(R.id.image_delete_trip_plan)
        mTextDelete = view.findViewById(R.id.delete_trip_plan)

        mImageSee.setOnClickListener {
            dialog.dismiss()
            mTripPlansAdminPresenter.onTripPlanVisualizationRequest(tripPlan)
        }
        mTextSee.setOnClickListener {
            dialog.dismiss()
            mTripPlansAdminPresenter.onTripPlanVisualizationRequest(tripPlan)
        }
        mImageCreate.setOnClickListener {
            dialog.dismiss()
            mTripPlansAdminPresenter.onItineraryProposalGenerationRequest(tripPlan)
        }
        mTextCreate.setOnClickListener {
            dialog.dismiss()
            mTripPlansAdminPresenter.onItineraryProposalGenerationRequest(tripPlan)
        }
        mImageDelete.setOnClickListener {
            dialog.dismiss()
            mTripPlansAdminPresenter.onTripPlanRemovalRequest(tripPlan)
        }
        mTextDelete.setOnClickListener {
            dialog.dismiss()
            mTripPlansAdminPresenter.onTripPlanRemovalRequest(tripPlan)
        }
        dialog.setContentView(view)
        dialog.show()
    }

}
