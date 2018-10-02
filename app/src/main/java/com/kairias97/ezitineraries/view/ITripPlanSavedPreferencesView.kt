package com.kairias97.ezitineraries.view

import com.kairias97.ezitineraries.model.TouristTripPreference
import com.kairias97.ezitineraries.model.TripPlan

interface ITripPlanSavedPreferencesView : IBaseView{
    fun showTripPlanPreferencesUI(preferences: List<TouristTripPreference>)
    fun navigateToTripPlanVisualization(tripPlan: TripPlan)

}