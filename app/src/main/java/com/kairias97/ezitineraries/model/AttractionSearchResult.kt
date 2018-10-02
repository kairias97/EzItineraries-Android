package com.kairias97.ezitineraries.model

import com.google.gson.annotations.SerializedName

class AttractionSearchResult {

    var attractionName: String = ""
    var address: String = ""
    var phone: String? = ""
    var websiteUrl: String? = ""
    var rating: Double? = null
    var googlePlaceId: String = ""
    var geolocation: Geolocation? = null

}