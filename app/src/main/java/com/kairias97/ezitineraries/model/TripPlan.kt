package com.kairias97.ezitineraries.model

import android.os.Parcel
import android.os.Parcelable
import com.kairias97.ezitineraries.data.local.AppDatabase
import com.raizlabs.android.dbflow.annotation.*
import com.raizlabs.android.dbflow.sql.language.SQLite
import com.raizlabs.android.dbflow.structure.BaseModel
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper
import java.util.*
import javax.inject.Inject


@Table(name = "trip_plan", database = AppDatabase::class,
        allFields = true)
class TripPlan @Inject constructor() : BaseModel(), Parcelable {
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    var id : Int = 0

    @NotNull
    @Column(name = "name")
    var name : String? = null


    @Column(name = "description")
    var description : String? = null

    @NotNull
    @Column(name = "tourist_visit_date")
    var date : Date? = null

    @ForeignKey(saveForeignKeyModel = true,
            onDelete = ForeignKeyAction.CASCADE,
            references = [(ForeignKeyReference(columnName = "city_id", foreignKeyColumnName = "id"))],
            tableClass = City::class)
    var city: City? = null

    @ColumnIgnore
    var preferences : List<TouristTripPreference>? = null


    @OneToMany(methods = [OneToMany.Method.ALL], variableName = "preferences")
    fun getTouristPreferences(): List<TouristTripPreference> {
        if(preferences == null || preferences!!.isEmpty()) {
            preferences = SQLite.select()
                    .from<TouristTripPreference>(TouristTripPreference::class.java)
                    .where(TouristTripPreference_Table.trip_plan_id.eq(id))
                    .queryList()
        }
        return preferences!!
    }
    @NotNull
    @ColumnMap
    var restriction : ItineraryRestriction? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        name = parcel.readString()
        description = parcel.readString()
        date = Date(parcel.readLong())
        city = parcel.readParcelable(City::class.java.classLoader)
        preferences = parcel.createTypedArrayList(TouristTripPreference)
        restriction = parcel.readParcelable(ItineraryRestriction::class.java.classLoader)
    }

    override fun save(wrapper:DatabaseWrapper): Boolean {
        var resultCountry = this.city?.country?.save(wrapper)
        var resultCity =this.city?.save(wrapper)
        val result =  this.save()
        if (preferences != null) {
            for (preference in preferences!!) {
                preference.tripPlanId = this.id
                preference.save(wrapper)
            }
        }
        return result
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeLong(date!!.time)
        parcel.writeParcelable(city, flags)
        parcel.writeTypedList(preferences)
        parcel.writeParcelable(restriction, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TripPlan> {
        override fun createFromParcel(parcel: Parcel): TripPlan {
            return TripPlan(parcel)
        }

        override fun newArray(size: Int): Array<TripPlan?> {
            return arrayOfNulls(size)
        }
    }


}