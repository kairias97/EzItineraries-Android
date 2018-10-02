package com.kairias97.ezitineraries.view

import com.kairias97.ezitineraries.model.TouristAttraction

interface ITripPlanAttractionsVisualizationView : IBaseView {
    fun showSaveTripPlanButton()
    fun showTouristAttractionsUI(attractions: List<TouristAttraction>)
    fun navigateToMainScreen()
    fun showSavedTripPlanSuccessMessage()
    fun showTripPlanSavingProgressMessage()
    fun hideTripPlanSavingProgressMessage()
}