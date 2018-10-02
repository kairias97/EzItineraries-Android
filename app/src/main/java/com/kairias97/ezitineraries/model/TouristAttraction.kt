package com.kairias97.ezitineraries.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.kairias97.ezitineraries.data.local.AppDatabase
import com.raizlabs.android.dbflow.annotation.*
import com.raizlabs.android.dbflow.structure.BaseModel
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper

@Table(name = "tourist_attraction", database = AppDatabase::class,
        allFields = true)
class TouristAttraction() : BaseModel(), Parcelable{
    @SerializedName("id")
    @PrimaryKey
    @Column(name = "id")
    var id : Int? = null


    @SerializedName("name")
    @NotNull
    @Column(name = "name")
    var name : String? = null

    @SerializedName("address")
    @NotNull
    @Column(name = "address")
    var address : String? = null

    @SerializedName("googlePlaceId")
    @NotNull
    @Unique
    @Column(name = "google_place_id")
    var googlePlaceId : String? = null

    @SerializedName("phoneNumber")
    @Column(name="phone_number")
    var phoneNumber : String? = null

    @SerializedName("websiteUrl")
    @Column(name = "website_url")
    var websiteUrl : String? = null

    @SerializedName("rating")
    @Column(name = "rating")
    var rating : Double? = null

    @SerializedName("city")
    @ForeignKey(saveForeignKeyModel =  true,
            onDelete = ForeignKeyAction.CASCADE,
            stubbedRelationship = false,
            tableClass = City::class,
            references = [(ForeignKeyReference(columnName = "city_id", foreignKeyColumnName = "id"))])
    var city : City? = null

    @SerializedName("category")
    @ForeignKey(saveForeignKeyModel =  true,
            stubbedRelationship = false,
            onDelete = ForeignKeyAction.CASCADE,
            tableClass = Category::class,
            references = [(ForeignKeyReference(columnName = "category_id", foreignKeyColumnName = "id"))])
    var category : Category? = null

    @SerializedName("geoposition")
    @ColumnMap
    var geolocation : Geolocation? = null

    @NotNull
    @SerializedName("active")
    @Column(name = "active", getterName = "isActive")
    var isActive: Boolean? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()
        address = parcel.readString()
        googlePlaceId = parcel.readString()
        phoneNumber = parcel.readString()
        websiteUrl = parcel.readString()
        rating = parcel.readValue(Double::class.java.classLoader) as? Double
        city = parcel.readParcelable(City::class.java.classLoader)
        category = parcel.readParcelable(Category::class.java.classLoader)
        geolocation = parcel.readParcelable(Geolocation::class.java.classLoader)
        isActive = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(googlePlaceId)
        parcel.writeString(phoneNumber)
        parcel.writeString(websiteUrl)
        parcel.writeValue(rating)
        parcel.writeParcelable(city, flags)
        parcel.writeParcelable(category, flags)
        parcel.writeParcelable(geolocation, flags)
        parcel.writeValue(isActive)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TouristAttraction> {
        override fun createFromParcel(parcel: Parcel): TouristAttraction {
            return TouristAttraction(parcel)
        }

        override fun newArray(size: Int): Array<TouristAttraction?> {
            return arrayOfNulls(size)
        }
    }

    override fun save(databaseWrapper: DatabaseWrapper): Boolean {
        var countrySaveResult = this.city?.country?.save(databaseWrapper)
        var citySaveResult = this.city?.save(databaseWrapper)
        var categorySaveResult = this.category?.save(databaseWrapper)
        var result = this.save()

        return result
    }

}