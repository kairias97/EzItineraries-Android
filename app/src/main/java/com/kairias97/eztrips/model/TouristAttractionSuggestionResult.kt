package com.kairias97.eztrips.model

import com.google.gson.annotations.SerializedName

class TouristAttractionSuggestionResult {
    @SerializedName("isSuccessful")
    var isSuccessful : Boolean? = null
    @SerializedName("message")
    var message : String? = null
}