package com.kairias97.ezitineraries.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.model.TripPlan
import com.kairias97.ezitineraries.model.TripPlanHeader

class TripPlanAdapter: RecyclerView.Adapter<TripPlanViewHolder> {
    private var tripPlans: MutableList<TripPlanHeader>
    private var listener: OnTripPlanSelectedListener

    interface OnTripPlanSelectedListener{
        fun onTripPlanSelected(tripPlan: TripPlanHeader)
    }
    constructor(tripPlans: MutableList<TripPlanHeader>, listener: OnTripPlanSelectedListener){
        this.tripPlans = tripPlans
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TripPlanViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(viewType, parent, false)
        return TripPlanViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tripPlans.count()
    }

    override fun onBindViewHolder(holder: TripPlanViewHolder?, position: Int) {
        (holder as TripPlanViewHolder).bindData(tripPlans[position], this.listener)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_trip_plan
    }
    fun setTripPlansList(list: MutableList<TripPlanHeader>){
        this.tripPlans = list
    }
    fun removeTripPlan(tripPlan: TripPlanHeader){
        this.tripPlans.remove(tripPlan)
    }
}