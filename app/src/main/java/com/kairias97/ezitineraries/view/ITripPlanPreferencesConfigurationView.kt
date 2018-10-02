package com.kairias97.ezitineraries.view

import com.kairias97.ezitineraries.model.TouristTripPreference
import com.kairias97.ezitineraries.model.TripPlan

interface ITripPlanPreferencesConfigurationView : IBaseView{
    fun showCategoryPreferencesListUI(preferences: List<TouristTripPreference>)
    fun showCategoriesLoadProgressMessage()
    fun hideCategoriesLoadProgressMessage()
    fun showInvalidWeightsMessage()
    fun navigateToLastStep(tripPlan: TripPlan)
}