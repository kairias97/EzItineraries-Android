package com.kairias97.ezitineraries.data

import com.kairias97.ezitineraries.model.CalculatedItinerary
import com.kairias97.ezitineraries.model.CalculatedItineraryHeader
import com.kairias97.ezitineraries.model.ItineraryRequest

interface ItinerariesRepository {
    fun getItinerariesHeaders(callback: OperationWithResultCallback<List<CalculatedItineraryHeader>>)
    fun getItineraryInfoById(itineraryId: Int,
                             callback: OperationWithResultCallback<CalculatedItinerary>)
    fun generateItineraryProposal(tripPlanId: Int,
                                  callback: OperationWithResultCallback<CalculatedItinerary>)
    fun saveItinerary(itinerary: CalculatedItinerary,
                            callback: OperationWithNoResultCallback)
    fun deleteItineraryById(itineraryId: Int,
                            callback: OperationWithNoResultCallback)
}