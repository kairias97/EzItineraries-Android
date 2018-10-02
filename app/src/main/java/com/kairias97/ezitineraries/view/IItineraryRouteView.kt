package com.kairias97.ezitineraries.view

import com.kairias97.ezitineraries.model.TouristAttraction
import com.kairias97.ezitineraries.model.TouristVisit

interface IItineraryRouteView : IBaseView{
    fun showTouristAttractions(attractions: List<TouristAttraction>)
    fun traceVisitRoutes(visits: List<TouristVisit>)
    fun navigateToMainScreen()
    fun showSavedItineraryMessage()
    fun showItinerarySaveButton()
    fun showItinerarySavingProgressMessage()
    fun hideItinerarySavingProgressMessage()
}