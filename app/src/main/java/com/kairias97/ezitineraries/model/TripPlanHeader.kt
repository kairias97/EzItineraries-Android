package com.kairias97.ezitineraries.model

import com.kairias97.ezitineraries.data.local.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.QueryModel
import java.util.*

@QueryModel(database = AppDatabase::class)
class TripPlanHeader {

    @Column(name="trip_plan_id")
    var tripPlanId : Int? = null


    @Column(name= "name")
    var name : String? = null

    @Column(name= "city")
    var city : String? = null

    @Column(name= "country")
    var country : String? = null


    @Column(name= "visit_date")
    var visitDate : Date? = null



}