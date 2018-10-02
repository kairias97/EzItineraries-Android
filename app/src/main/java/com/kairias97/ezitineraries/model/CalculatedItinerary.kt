package com.kairias97.ezitineraries.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.kairias97.ezitineraries.data.local.AppDatabase
import com.raizlabs.android.dbflow.annotation.*
import com.raizlabs.android.dbflow.kotlinextensions.save
import com.raizlabs.android.dbflow.structure.BaseModel
import java.util.*
import com.raizlabs.android.dbflow.sql.language.SQLite
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper
import javax.inject.Inject


@Table(name = "calculated_itinerary", database = AppDatabase::class,
        allFields = true)
class CalculatedItinerary @Inject constructor() : BaseModel(), Parcelable {
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    var id : Int = 0

    @SerializedName("score")
    @NotNull
    @Column(name = "score")
    var score : Double? = null

    @SerializedName("calculatedKmDistance")
    @NotNull
    @Column(name = "calculated_km_distance")
    var calculatedKmDistance : Double? = null

    @NotNull
    @Column(name = "visit_date")
    var visitDate : Date? = null

    @ForeignKey(saveForeignKeyModel = true,
            references = [(ForeignKeyReference(columnName = "city_id", foreignKeyColumnName = "id"))],
            onDelete = ForeignKeyAction.CASCADE,
            tableClass = City::class)
    var city: City? = null

    @NotNull
    @ColumnMap
    var restriction: ItineraryRestriction? = null


    @ColumnIgnore
    var itineraryPreferences : List<ItineraryTouristPreference>? = null

    @OneToMany(methods = [OneToMany.Method.ALL], variableName = "itineraryPreferences")
    fun getPreferences(): List<ItineraryTouristPreference> {
        if(itineraryPreferences == null || itineraryPreferences!!.isEmpty()) {

            itineraryPreferences = SQLite.select()
                    .from<ItineraryTouristPreference>(ItineraryTouristPreference::class.java!!)
                    .where(ItineraryTouristPreference_Table.calculated_itinerary_id.eq(id))
                    .queryList()
        }
        return itineraryPreferences!!
    }

    @OneToMany(methods = [OneToMany.Method.ALL], variableName = "touristVisits")
    fun getVisits(): List<TouristVisit> {
        if(touristVisits == null || touristVisits!!.isEmpty()) {
            touristVisits = SQLite.select()
                    .from<TouristVisit>(TouristVisit::class.java)
                    .where(ItineraryTouristPreference_Table.calculated_itinerary_id.eq(id))
                    .queryList()
        }
        return touristVisits!!
    }
    @ColumnIgnore
    var touristVisits : List<TouristVisit>? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        score = parcel.readValue(Double::class.java.classLoader) as? Double
        calculatedKmDistance = parcel.readValue(Double::class.java.classLoader) as? Double
        visitDate = Date(parcel.readLong())
        city = parcel.readParcelable(City::class.java.classLoader)
        restriction = parcel.readParcelable(ItineraryRestriction::class.java.classLoader)
        itineraryPreferences = parcel.createTypedArrayList(ItineraryTouristPreference)
        touristVisits = parcel.createTypedArrayList(TouristVisit)
    }

    override fun save(databaseWrapper: DatabaseWrapper): Boolean {
        var resultCountry = this.city?.country?.save(databaseWrapper)
        var resultCity =this.city?.save(databaseWrapper)
        val result =  this.save()
        if (itineraryPreferences != null) {
            for (preference in itineraryPreferences!!) {
                preference.calculatedItineraryId = this.id
                preference.save(databaseWrapper)
            }
        }
        if (touristVisits != null) {
            for (visits in touristVisits!!) {
                visits.calculatedItineraryId = this.id
                visits.save(databaseWrapper)
            }
        }
        return result
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeValue(score)
        parcel.writeValue(calculatedKmDistance)
        parcel.writeLong(visitDate!!.time)
        parcel.writeParcelable(city, flags)
        parcel.writeParcelable(restriction, flags)
        parcel.writeTypedList(itineraryPreferences)
        parcel.writeTypedList(touristVisits)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CalculatedItinerary> {
        override fun createFromParcel(parcel: Parcel): CalculatedItinerary {
            return CalculatedItinerary(parcel)
        }

        override fun newArray(size: Int): Array<CalculatedItinerary?> {
            return arrayOfNulls(size)
        }
    }
}