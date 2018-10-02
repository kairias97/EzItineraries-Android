package com.kairias97.ezitineraries.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.model.ItineraryTouristPreference
import com.kairias97.ezitineraries.model.TouristTripPreference

class ItineraryPreferenceAdapter: RecyclerView.Adapter<ItineraryPreferenceViewHolder> {
    private var preferences: MutableList<ItineraryTouristPreference>
    constructor(preferences: MutableList<ItineraryTouristPreference>){
        this.preferences = preferences
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItineraryPreferenceViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(viewType, parent, false)
        return ItineraryPreferenceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return preferences.count()
    }

    override fun onBindViewHolder(holder: ItineraryPreferenceViewHolder?, position: Int) {
        (holder as ItineraryPreferenceViewHolder).bindData(preferences[position])
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_saved_preference
    }
    fun setPreferenceList(list: MutableList<ItineraryTouristPreference>){
        this.preferences = list
    }
}