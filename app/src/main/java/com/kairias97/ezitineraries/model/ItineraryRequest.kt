package com.kairias97.ezitineraries.model

import com.google.gson.annotations.SerializedName

class ItineraryRequest {
    @SerializedName("cityId")
    var cityId : Int? = null
    @SerializedName("maxDistance")
    var maxDistance: Double? = null
    @SerializedName("preferences")
    var preferences : List<CategoryPreference>? = null
}