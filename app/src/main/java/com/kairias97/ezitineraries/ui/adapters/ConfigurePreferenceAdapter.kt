package com.kairias97.ezitineraries.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.model.TouristTripPreference

class ConfigurePreferenceAdapter:RecyclerView.Adapter<ConfigurePreferenceViewHolder> {
    private var preferences: MutableList<TouristTripPreference>
    constructor(preferences: MutableList<TouristTripPreference>){
        this.preferences = preferences
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ConfigurePreferenceViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(viewType, parent, false)
        return ConfigurePreferenceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return preferences.count()
    }

    override fun onBindViewHolder(holder: ConfigurePreferenceViewHolder?, position: Int) {
        (holder as ConfigurePreferenceViewHolder).bindData(preferences[position])
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_preferences
    }
    fun setConfigurePreferencesList(list: MutableList<TouristTripPreference>){
        this.preferences = list
    }
    fun getPreferencesList():List<TouristTripPreference>{
        return this.preferences.toList()
    }
}