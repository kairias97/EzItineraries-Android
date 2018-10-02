package com.kairias97.ezitineraries.presenter

import com.kairias97.ezitineraries.model.CalculatedItineraryHeader
import com.kairias97.ezitineraries.view.IItinerariesAdminView

interface IItinerariesAdminPresenter : BasePresenter<IItinerariesAdminView>{
    fun onViewItineraryRequest(itineraryHeader: CalculatedItineraryHeader)
    fun onItineraryRemovalRequest(itineraryHeader: CalculatedItineraryHeader)
    fun provideItineraryHeaders()
}