package com.kairias97.ezitineraries.view

import com.kairias97.ezitineraries.model.CalculatedItinerary
import com.kairias97.ezitineraries.model.TripPlanHeader

interface ITripPlansAdminView : IBaseView{
    fun navigateToNewTripPlanDestinationScreen()
    fun navigateToViewTripPlanDestinationScreen(tripPlanId: Int)
    fun removeTripPlanHeaderFromUI(tripPlanHeader: TripPlanHeader)
    fun showTripPlanHeaders(tripPlanHeaders: List<TripPlanHeader>)
    fun showItineraryRequestProgressMessage()
    fun hideItineraryRequestProgressMessage()
    fun navigateToViewCalculatedItineraryScreen(calculatedItinerary: CalculatedItinerary)
}