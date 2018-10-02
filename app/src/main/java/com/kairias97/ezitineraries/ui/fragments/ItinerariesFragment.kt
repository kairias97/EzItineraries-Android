package com.kairias97.ezitineraries.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.di.ActivityScoped
import com.kairias97.ezitineraries.model.CalculatedItineraryHeader
import com.kairias97.ezitineraries.presenter.IItinerariesAdminPresenter
import com.kairias97.ezitineraries.ui.activities.ItineraryDetailActivity
import com.kairias97.ezitineraries.ui.adapters.ItineraryAdapter
import com.kairias97.ezitineraries.utils.toast
import com.kairias97.ezitineraries.view.IItinerariesAdminView
import dagger.android.support.DaggerFragment
import javax.inject.Inject

@ActivityScoped
class ItinerariesFragment @Inject constructor() : DaggerFragment(), IItinerariesAdminView, ItineraryAdapter.OnItinerarySelectedListener {

    @Inject lateinit var mItinerariesAdminPresenter : IItinerariesAdminPresenter

    private lateinit var mRecyclerView: RecyclerView

    private lateinit var mItinerariesAdapter: ItineraryAdapter

    private lateinit var mTextView: TextView

    private lateinit var mImageSee: ImageView
    private lateinit var mTextSee: TextView
    private lateinit var mImageDelete: ImageView
    private lateinit var mTextDelete: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        activity.title =  getString(R.string.title_fragment_itineraries)
        val view = inflater.inflate(R.layout.fragment_itineraries, container, false)
        mRecyclerView = view.findViewById(R.id.recycler_itineraries)
        mTextView = view.findViewById(R.id.textView_label_no_itineraries)
        mItinerariesAdapter = ItineraryAdapter(ArrayList<CalculatedItineraryHeader>(), this)
        mRecyclerView.adapter = mItinerariesAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.setHasFixedSize(true)

        mItinerariesAdminPresenter.takeView(this)
        mItinerariesAdminPresenter.provideItineraryHeaders()
        return view
    }

    override fun onResume() {
        mItinerariesAdminPresenter.takeView(this)
        super.onResume()
    }
    override fun onDetach() {
        mItinerariesAdminPresenter.dropView()
        super.onDetach()
    }

    override fun onDestroy() {
        mItinerariesAdminPresenter.dropView()
        super.onDestroy()
    }

    override fun navigateToItineraryVisualization(itineraryId: Int) {
        val intent = Intent(activity, ItineraryDetailActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_NO_HISTORY
        intent.putExtra("calculatedItineraryId",itineraryId)
        startActivity(intent)
    }

    override fun showItinerariesHeaders(itinerariesList: List<CalculatedItineraryHeader>) {
        if(itinerariesList.count()>0){
            (mRecyclerView.adapter as? ItineraryAdapter)!!.setItinerariesList(itinerariesList.toMutableList())
            (mRecyclerView.adapter as? ItineraryAdapter)!!.notifyDataSetChanged()
            mTextView.visibility = View.GONE
        }
        else{
            mTextView.visibility = View.VISIBLE
        }
    }

    override fun removeItineraryHeaderFromUI(itineraryHeader: CalculatedItineraryHeader) {
        (mRecyclerView.adapter as ItineraryAdapter).removeItinerary(itineraryHeader)
        (mRecyclerView.adapter as ItineraryAdapter).notifyDataSetChanged()
    }

    override fun showCommunicationErrorMessage() {
        activity.toast(R.string.generic_500_error)
    }
    override fun onItinerarySelected(itinerary: CalculatedItineraryHeader) {
        val dialog = BottomSheetDialog(activity)
        val view = layoutInflater.inflate(R.layout.menu_itineraries, null)
        mImageSee = view.findViewById(R.id.image_see_itinerary_details)
        mTextSee = view.findViewById(R.id.see_itinerary_details)
        mImageDelete = view.findViewById(R.id.image_delete_itinerary)
        mTextDelete = view.findViewById(R.id.delete_itinerary)

        mImageSee.setOnClickListener {
            dialog.dismiss()
            mItinerariesAdminPresenter.onViewItineraryRequest(itinerary)
        }
        mTextSee.setOnClickListener {
            dialog.dismiss()
            mItinerariesAdminPresenter.onViewItineraryRequest(itinerary)
        }
        mImageDelete.setOnClickListener {
            dialog.dismiss()
            mItinerariesAdminPresenter.onItineraryRemovalRequest(itinerary)
        }
        mTextDelete.setOnClickListener {
            dialog.dismiss()
            mItinerariesAdminPresenter.onItineraryRemovalRequest(itinerary)
        }
        dialog.setContentView(view)
        dialog.show()
    }


}
