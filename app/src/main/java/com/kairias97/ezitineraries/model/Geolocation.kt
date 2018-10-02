package com.kairias97.ezitineraries.model

import android.os.Parcel
import android.os.Parcelable
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.NotNull

class Geolocation() : Parcelable {
    @NotNull
    @Column(name = "latitude")
    var latitude : Double? = null
    @NotNull
    @Column(name = "longitude")
    var longitude : Double? = null

    constructor(parcel: Parcel) : this() {
        latitude = parcel.readValue(Double::class.java.classLoader) as? Double
        longitude = parcel.readValue(Double::class.java.classLoader) as? Double
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(latitude)
        parcel.writeValue(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Geolocation> {
        override fun createFromParcel(parcel: Parcel): Geolocation {
            return Geolocation(parcel)
        }

        override fun newArray(size: Int): Array<Geolocation?> {
            return arrayOfNulls(size)
        }
    }

}