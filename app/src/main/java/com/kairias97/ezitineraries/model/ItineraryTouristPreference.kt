package com.kairias97.ezitineraries.model

import android.os.Parcel
import android.os.Parcelable
import com.kairias97.ezitineraries.data.local.AppDatabase
import com.raizlabs.android.dbflow.annotation.*
import com.raizlabs.android.dbflow.structure.BaseModel
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper

@Table(name = "itinerary_tourist_preference", database = AppDatabase::class,
        allFields = true)
class ItineraryTouristPreference() : BaseModel(), Parcelable{
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    var id : Int = 0

    @NotNull
    @Column(name = "weight")
    var weight : Double? = null

    @ForeignKey(saveForeignKeyModel = true,
            tableClass = Category::class,
            onDelete = ForeignKeyAction.CASCADE,
            references = [(ForeignKeyReference(columnName = "category_id",
                    foreignKeyColumnName = "id"))])
    var category: Category? = null


    @ForeignKey(tableClass = CalculatedItinerary::class,
            references = [(ForeignKeyReference(columnName = "calculated_itinerary_id",
                    foreignKeyColumnName = "id"))],
            onDelete = ForeignKeyAction.CASCADE)
    var calculatedItineraryId : Int? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        weight = parcel.readValue(Double::class.java.classLoader) as? Double
        category = parcel.readParcelable(Category::class.java.classLoader)
        calculatedItineraryId = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeValue(weight)
        parcel.writeParcelable(category, flags)
        parcel.writeValue(calculatedItineraryId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItineraryTouristPreference> {
        override fun createFromParcel(parcel: Parcel): ItineraryTouristPreference {
            return ItineraryTouristPreference(parcel)
        }

        override fun newArray(size: Int): Array<ItineraryTouristPreference?> {
            return arrayOfNulls(size)
        }
    }

    override fun save(databaseWrapper: DatabaseWrapper): Boolean {
        var categoryResult = this.category?.save(databaseWrapper)
        var result = this.save()
        return result
    }


}