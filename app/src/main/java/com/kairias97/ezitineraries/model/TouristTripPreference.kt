package com.kairias97.ezitineraries.model

import android.os.Parcel
import android.os.Parcelable
import com.kairias97.ezitineraries.data.local.AppDatabase
import com.raizlabs.android.dbflow.annotation.*
import com.raizlabs.android.dbflow.structure.BaseModel
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper

@Table(name = "trip_preference", database = AppDatabase::class,
        allFields = true)
class TouristTripPreference() : BaseModel(), Parcelable {
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    var id : Int = 0

    @NotNull
    @Column(name = "weight")
    var assignedWeight : Double? = null

    @ForeignKey(tableClass = TripPlan::class,
            references = [(ForeignKeyReference(columnName = "trip_plan_id",
                    foreignKeyColumnName = "id"))],
            onDelete = ForeignKeyAction.CASCADE)
    var tripPlanId : Int? = null

    @ForeignKey(tableClass = Category::class,
            saveForeignKeyModel = true,
            references = [(ForeignKeyReference(columnName = "category_id",
                    foreignKeyColumnName = "id"))],
            onDelete = ForeignKeyAction.CASCADE)
    var category: Category? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        assignedWeight = parcel.readValue(Double::class.java.classLoader) as? Double
        tripPlanId = parcel.readValue(Int::class.java.classLoader) as? Int
        category = parcel.readParcelable(Category::class.java.classLoader)
    }
    override fun save(databaseWrapper: DatabaseWrapper): Boolean {
        var categoryResult = this.category?.save(databaseWrapper)
        var result = this.save()
        return result
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeValue(assignedWeight)
        parcel.writeValue(tripPlanId)
        parcel.writeParcelable(category, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TouristTripPreference> {
        override fun createFromParcel(parcel: Parcel): TouristTripPreference {
            return TouristTripPreference(parcel)
        }

        override fun newArray(size: Int): Array<TouristTripPreference?> {
            return arrayOfNulls(size)
        }
    }


}