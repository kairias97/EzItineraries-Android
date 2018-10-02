package com.kairias97.ezitineraries.presenter

import com.kairias97.ezitineraries.view.IItineraryRouteView

interface IItineraryRoutePresenter : BasePresenter<IItineraryRouteView> {
    fun provideTouristVisits()
    fun onItinerarySaveRequest()
}