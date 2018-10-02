package com.kairias97.ezitineraries.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.model.TouristTripPreference

class SavedPreferenceAdapter:RecyclerView.Adapter<SavedPreferenceViewHolder> {
    private var preferences: MutableList<TouristTripPreference>
    constructor(preferences: MutableList<TouristTripPreference>){
        this.preferences = preferences
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SavedPreferenceViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(viewType, parent, false)
        return SavedPreferenceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return preferences.count()
    }

    override fun onBindViewHolder(holder: SavedPreferenceViewHolder?, position: Int) {
        (holder as SavedPreferenceViewHolder).bindData(preferences[position])
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_saved_preference
    }
    fun setPreferenceList(list: MutableList<TouristTripPreference>){
        this.preferences = list
    }
}