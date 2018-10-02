package com.kairias97.ezitineraries.ui.adapters

import android.support.design.widget.TextInputLayout
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.model.TouristTripPreference

class ConfigurePreferenceViewHolder: RecyclerView.ViewHolder {
    private val mEditText: EditText
    private val mTextInputLayout: TextInputLayout
    private lateinit var mPreference: TouristTripPreference

    constructor(itemView: View):super(itemView){
        this.mEditText = itemView.findViewById(R.id.edit_text_preference)
        this.mTextInputLayout = itemView.findViewById(R.id.text_input_layout_preference)

        mEditText.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(mEditText.text.isNotEmpty()){
                    mPreference.assignedWeight = mEditText.text.toString().toDouble()/100
                }
                else{
                    mPreference.assignedWeight = "0".toDouble()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }
    fun bindData(preference: TouristTripPreference){
        mPreference = preference
        this.mTextInputLayout.hint = preference.category?.name
    }
}