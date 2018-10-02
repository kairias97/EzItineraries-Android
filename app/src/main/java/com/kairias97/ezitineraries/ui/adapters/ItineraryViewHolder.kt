package com.kairias97.ezitineraries.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.model.CalculatedItineraryHeader
import com.kairias97.ezitineraries.utils.DateUtil
import java.math.RoundingMode
import java.text.DecimalFormat

class ItineraryViewHolder:RecyclerView.ViewHolder {
    private val mTxtPlace: TextView
    private val mTxtDate: TextView
    private val mTxtScore: TextView

    constructor(itemView: View):super(itemView){
        mTxtPlace = itemView.findViewById(R.id.textVIew_place)
        mTxtDate = itemView.findViewById(R.id.textView_date)
        mTxtScore = itemView.findViewById(R.id.textView_score)
    }

    fun bindData(itinerary: CalculatedItineraryHeader, listener: ItineraryAdapter.OnItinerarySelectedListener){
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        mTxtPlace.text = "${itinerary.city}, ${itinerary.country}"
        mTxtDate.text = DateUtil.parseDateToFormat(itinerary.visitDate!!, "dd/MM/yyyy")
        mTxtScore.text = df.format(itinerary.score).toString()

        itemView.setOnClickListener {
            listener.onItinerarySelected(itinerary)
        }
    }
}