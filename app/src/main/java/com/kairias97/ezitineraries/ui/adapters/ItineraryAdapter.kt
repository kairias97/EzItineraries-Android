package com.kairias97.ezitineraries.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.model.CalculatedItineraryHeader

class ItineraryAdapter: RecyclerView.Adapter<ItineraryViewHolder> {
    private var itineraries: MutableList<CalculatedItineraryHeader>
    private var listener: OnItinerarySelectedListener

    interface OnItinerarySelectedListener{
        fun onItinerarySelected(itinerary: CalculatedItineraryHeader)
    }

    constructor(itineraries: MutableList<CalculatedItineraryHeader>, listener: OnItinerarySelectedListener){
        this.itineraries = itineraries
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItineraryViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(viewType, parent, false)
        return ItineraryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itineraries.count()
    }

    override fun onBindViewHolder(holder: ItineraryViewHolder?, position: Int) {
        (holder as ItineraryViewHolder).bindData(itineraries[position], this.listener)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_itineraries
    }

    fun setItinerariesList(list: MutableList<CalculatedItineraryHeader>){
        this.itineraries = list
    }

    fun removeItinerary(itinerary: CalculatedItineraryHeader){
        this.itineraries.remove(itinerary)
    }
}