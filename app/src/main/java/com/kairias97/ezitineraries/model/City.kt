package com.kairias97.ezitineraries.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.kairias97.ezitineraries.data.local.AppDatabase
import com.raizlabs.android.dbflow.annotation.*
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(name = "city", database = AppDatabase::class,
        allFields = true)
class City() : BaseModel(), Parcelable{
    @PrimaryKey
    @Column(name = "id")
    @SerializedName("id")
    var cityId : Int? = null

    @NotNull
    @Column(name = "name")
    @SerializedName("name")
    var name: String? = ""


    @SerializedName("country")
    @ForeignKey(saveForeignKeyModel = true,
            tableClass = Country::class,
            references = [(ForeignKeyReference(columnName = "country_id",
                    foreignKeyColumnName = "iso_numeric_code"))],
            onDelete = ForeignKeyAction.CASCADE)
    var country: Country? = null

    constructor(parcel: Parcel) : this() {
        cityId = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()
        country = parcel.readParcelable(Country::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(cityId)
        parcel.writeString(name)
        parcel.writeParcelable(country, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<City> {
        override fun createFromParcel(parcel: Parcel): City {
            return City(parcel)
        }

        override fun newArray(size: Int): Array<City?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return name!!
    }
}