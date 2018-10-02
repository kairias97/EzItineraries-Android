package com.kairias97.ezitineraries.view

import com.kairias97.ezitineraries.model.CalculatedItinerary

interface IItineraryDetailView : IBaseView {
    fun navigateToItineraryPreferencesVisualization(calculatedItinerary: CalculatedItinerary,
                                                    isNew: Boolean)
    fun showItineraryInformation(itinerary:CalculatedItinerary)

}