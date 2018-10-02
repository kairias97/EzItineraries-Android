package com.kairias97.ezitineraries.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.NotNull

class ItineraryRestriction() : Parcelable {
    @SerializedName("maxDistance")
    @Column(name = "max_distance")
    @NotNull
    var maxDistance : Double? = null

    constructor(maxDistance: Double?): this() {
        this.maxDistance = maxDistance
    }

    constructor(parcel: Parcel) : this() {
        maxDistance = parcel.readValue(Double::class.java.classLoader) as? Double
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(maxDistance)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItineraryRestriction> {
        override fun createFromParcel(parcel: Parcel): ItineraryRestriction {
            return ItineraryRestriction(parcel)
        }

        override fun newArray(size: Int): Array<ItineraryRestriction?> {
            return arrayOfNulls(size)
        }
    }
}