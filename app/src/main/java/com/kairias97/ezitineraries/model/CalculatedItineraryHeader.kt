package com.kairias97.ezitineraries.model

import com.kairias97.ezitineraries.data.local.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.QueryModel
import java.util.*


@QueryModel(database = AppDatabase::class)
class CalculatedItineraryHeader {

    @Column(name="calculated_itinerary_id")
    var itineraryId : Int? = null


    @Column(name= "score")
    var score : Double? = null

    @Column(name= "city")
    var city : String? = null

    @Column(name= "country")
    var country : String? = null


    @Column(name= "visit_date")
    var visitDate : Date? = null



}