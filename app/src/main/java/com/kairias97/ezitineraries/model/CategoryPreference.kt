package com.kairias97.ezitineraries.model

import com.google.gson.annotations.SerializedName

class CategoryPreference {
    @SerializedName("weight")
    var weight : Double? = null

    @SerializedName("categoryId")
    var categoryId : Int? = null
}