package com.kairias97.eztrips.model

import com.google.gson.annotations.SerializedName
import com.kairias97.eztrips.data.local.AppDatabase
import com.raizlabs.android.dbflow.annotation.*
import com.raizlabs.android.dbflow.structure.BaseModel
import java.util.*

@Table(name = "calculated_itinerary", database = AppDatabase::class,
        allFields = true)
class CalculatedItinerary : BaseModel() {
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
    var visitDate : Date = Calendar.getInstance().time

    @ForeignKey(saveForeignKeyModel = true,
            references = [(ForeignKeyReference(columnName = "city_id", foreignKeyColumnName = "id"))],
            onDelete = ForeignKeyAction.CASCADE,
            tableClass = City::class)
    var city: City? = null

    @NotNull
    @ColumnMap
    var restriction: ItineraryRestriction? = null

    @ColumnIgnore
    var itineraryPreferences : MutableList<ItineraryTouristPreference> = arrayListOf()

    @ColumnIgnore
    var touristVisits : MutableList<TouristVisit> = mutableListOf()
}