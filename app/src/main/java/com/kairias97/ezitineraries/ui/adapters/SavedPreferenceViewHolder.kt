package com.kairias97.ezitineraries.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.model.TouristTripPreference

class SavedPreferenceViewHolder: RecyclerView.ViewHolder {
    private val mTxtCategoryTitle: TextView
    private val mTxtCategory: TextView

    constructor(itemView: View):super(itemView){
        this.mTxtCategoryTitle = itemView.findViewById(R.id.textView_category_title)
        this.mTxtCategory = itemView.findViewById(R.id.textVIew_category_score)
    }
    fun bindData(preference: TouristTripPreference){
        this.mTxtCategoryTitle.text = preference.category?.name
        val weight: Double = preference.assignedWeight!!.times(100)
        this.mTxtCategory.text = weight.toInt().toString()
    }

}