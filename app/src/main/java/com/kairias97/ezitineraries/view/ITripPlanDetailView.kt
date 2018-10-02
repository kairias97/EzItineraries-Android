package com.kairias97.ezitineraries.view

import com.kairias97.ezitineraries.model.TripPlan

interface ITripPlanDetailView : IBaseView {
    fun showTripPlanInformation(tripPlan: TripPlan)
    fun navigateToTripPlanPreferencesVisualization(tripPlan: TripPlan)
}