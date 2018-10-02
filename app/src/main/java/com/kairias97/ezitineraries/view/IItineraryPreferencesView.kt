package com.kairias97.ezitineraries.view

import com.kairias97.ezitineraries.model.CalculatedItinerary
import com.kairias97.ezitineraries.model.ItineraryTouristPreference

interface IItineraryPreferencesView : IBaseView{
    fun navigateToItineraryAttractionsVisualization(calculatedItinerary: CalculatedItinerary,
                                                    isNew: Boolean)
    fun showPreferencesUI(preferences: List<ItineraryTouristPreference>)
}