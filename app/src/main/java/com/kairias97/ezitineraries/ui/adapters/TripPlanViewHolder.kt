package com.kairias97.ezitineraries.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.model.TripPlan
import com.kairias97.ezitineraries.model.TripPlanHeader
import com.kairias97.ezitineraries.utils.DateUtil

class TripPlanViewHolder: RecyclerView.ViewHolder {
    private val mTxtName: TextView
    private val mTxtPlace: TextView
    private val mTxtDate: TextView

    constructor(itemView: View): super(itemView){
        mTxtName = itemView.findViewById(R.id.textView_name)
        mTxtPlace = itemView.findViewById(R.id.textView_place)
        mTxtDate = itemView.findViewById(R.id.textView_date)
    }
    fun bindData(tripPlan: TripPlanHeader, listener: TripPlanAdapter.OnTripPlanSelectedListener){
        mTxtName.text = tripPlan.name
        mTxtPlace.text = "${tripPlan.city}, ${tripPlan.country}"
        mTxtDate.text = DateUtil.parseDateToFormat(tripPlan.visitDate!!, "dd/MM/yyyy")

        itemView.setOnClickListener {
            listener.onTripPlanSelected(tripPlan)
        }
    }

}