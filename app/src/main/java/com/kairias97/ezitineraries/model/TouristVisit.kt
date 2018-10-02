package com.kairias97.ezitineraries.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.kairias97.ezitineraries.data.local.AppDatabase
import com.raizlabs.android.dbflow.annotation.*
import com.raizlabs.android.dbflow.structure.BaseModel
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper

@Table(name = "itinerary_tourist_visit", database = AppDatabase::class)
class TouristVisit() : BaseModel(), Parcelable{
    @PrimaryKey(autoincrement = true)
    @Column(name="id")
    var id : Int = 0

    @NotNull
    @SerializedName("order")
    @Column(name = "visit_order")
    var visitOrder : Int? = null

    @ForeignKey(onDelete = ForeignKeyAction.CASCADE,
            tableClass = TouristAttraction::class,
            saveForeignKeyModel = true,
            references = [(ForeignKeyReference(columnName = "tourist_attraction_id",
                    foreignKeyColumnName = "id"))]
            )
    var touristAttraction : TouristAttraction? = null


    @ForeignKey(tableClass = CalculatedItinerary::class,
            references = [(ForeignKeyReference(columnName = "calculated_itinerary_id",
                    foreignKeyColumnName = "id"))],
            onDelete = ForeignKeyAction.CASCADE)
    var calculatedItineraryId : Int? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        visitOrder = parcel.readValue(Int::class.java.classLoader) as? Int
        touristAttraction = parcel.readParcelable(TouristAttraction::class.java.classLoader)
        calculatedItineraryId = parcel.readValue(Int::class.java.classLoader) as? Int
    }
    override fun save(databaseWrapper: DatabaseWrapper): Boolean {
        var touristAttractionResult = this.touristAttraction?.save(databaseWrapper)
        var result = this.save()
        return result
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeValue(visitOrder)
        parcel.writeParcelable(touristAttraction, flags)
        parcel.writeValue(calculatedItineraryId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TouristVisit> {
        override fun createFromParcel(parcel: Parcel): TouristVisit {
            return TouristVisit(parcel)
        }

        override fun newArray(size: Int): Array<TouristVisit?> {
            return arrayOfNulls(size)
        }
    }
}