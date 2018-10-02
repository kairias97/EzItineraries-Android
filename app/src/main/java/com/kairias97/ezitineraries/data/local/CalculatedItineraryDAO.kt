package com.kairias97.ezitineraries.data.local

import com.kairias97.ezitineraries.data.OperationWithNoResultCallback
import com.kairias97.ezitineraries.data.OperationWithResultCallback
import com.kairias97.ezitineraries.model.CalculatedItinerary
import com.kairias97.ezitineraries.model.CalculatedItineraryHeader
import com.kairias97.ezitineraries.model.TripPlan

interface CalculatedItineraryDAO {
    fun saveItinerary(itinerary : CalculatedItinerary, callback: OperationWithNoResultCallback?)
    fun deleteItineraryById(itineraryId : Int, callback: OperationWithNoResultCallback?)
    fun getItineraryById(itineraryId: Int, callback: OperationWithResultCallback<CalculatedItinerary>?)
    fun getItinerariesHeaders(callback: OperationWithResultCallback<List<CalculatedItineraryHeader>>?)
}