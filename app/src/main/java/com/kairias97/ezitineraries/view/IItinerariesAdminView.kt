package com.kairias97.ezitineraries.view

import com.kairias97.ezitineraries.model.CalculatedItineraryHeader

interface IItinerariesAdminView : IBaseView{
    fun navigateToItineraryVisualization(itineraryId: Int)
    fun showItinerariesHeaders(itinerariesList: List<CalculatedItineraryHeader>)
    fun removeItineraryHeaderFromUI(itineraryHeader: CalculatedItineraryHeader)


}