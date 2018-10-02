package com.kairias97.ezitineraries.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.kairias97.ezitineraries.data.local.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.NotNull
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(name = "country", database = AppDatabase::class)
 class Country() : BaseModel(), Parcelable {
    @Column(name = "iso_numeric_code")
    @PrimaryKey
    @SerializedName("isoNumericCode")
    var isoNumericCode : String? = null

   @NotNull
    @Column(name = "name")
    @SerializedName("name")
    var name: String? = null

   @NotNull
    @Column(name = "alpha2_code")
    @SerializedName("alpha2Code")
    var alpha2Code : String? = null

   @NotNull
    @Column(name = "alpha3_code")
    @SerializedName("alpha3Code")
    var alpha3Code : String? = null

    constructor(parcel: Parcel) : this() {
        isoNumericCode = parcel.readString()
        name = parcel.readString()
        alpha2Code = parcel.readString()
        alpha3Code = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(isoNumericCode)
        parcel.writeString(name)
        parcel.writeString(alpha2Code)
        parcel.writeString(alpha3Code)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Country> {
        override fun createFromParcel(parcel: Parcel): Country {
            return Country(parcel)
        }

        override fun newArray(size: Int): Array<Country?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return name!!
    }
}