package com.kairias97.eztrips.model

import com.kairias97.eztrips.data.local.AppDatabase
import com.raizlabs.android.dbflow.annotation.*
import com.raizlabs.android.dbflow.structure.BaseModel
import java.util.*


@Table(name = "trip_plan", database = AppDatabase::class,
        allFields = true)
class TripPlan : BaseModel() {
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    var id : Int = 0

    @NotNull
    @Column(name = "name")
    var name : String? = null

    @NotNull
    @Column(name = "description")
    var description : String? = null

    @NotNull
    @Column(name = "tourist_visit_date")
    var date : Date = Calendar.getInstance().time

    @ForeignKey(saveForeignKeyModel = true,
            onDelete = ForeignKeyAction.CASCADE,
            references = [(ForeignKeyReference(columnName = "city_id", foreignKeyColumnName = "id"))],
            tableClass = City::class)
    var city: City? = null

    @ColumnIgnore
    var preferences : MutableList<TouristTripPreference> = mutableListOf()

    @NotNull
    @ColumnMap
    var restriction : ItineraryRestriction? = null




}