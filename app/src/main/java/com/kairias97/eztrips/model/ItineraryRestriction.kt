package com.kairias97.eztrips.model

import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.NotNull

class ItineraryRestriction {
    @SerializedName("maxDistance")
    @Column(name = "max_distance")
    @NotNull
    var maxDistance : Double? = null
}